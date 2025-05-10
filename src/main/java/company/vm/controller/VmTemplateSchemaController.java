package company.vm.controller;


import company.vm.dto.controller.VmTemplateSchemaResponseDto;
import company.vm.dto.service.template.*;
import company.vm.mapper.VmTemplateSchemaMapper;
import company.vm.service.VmTemplateSchemaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@RestController
@RequestMapping("/api/v1/vm-template-schemes")
@Tag(
        name = "Схемы vm-шаблона",
        description = "API для создания схем vm-шаблонов"
)
public class VmTemplateSchemaController {
    private final VmTemplateSchemaService vmTemplateSchemaService;
    private final VmTemplateSchemaMapper vmTemplateSchemaMapper;

    public VmTemplateSchemaController(VmTemplateSchemaService vmTemplateSchemaService,
                                      VmTemplateSchemaMapper vmTemplateSchemaMapper) {
        this.vmTemplateSchemaService = vmTemplateSchemaService;
        this.vmTemplateSchemaMapper = vmTemplateSchemaMapper;
    }

    @Operation(
            summary = "Скачать схемы vm-шаблона по Id",
            description = "Позволяет получить схемы vm-шаблонов по версии (id), включая базовую и вложенную схемы в виде zip-архива.",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор версии vm-шаблона",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Схемы vm-шаблона успешно загружены в zip-архиве",
                            content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Схемы vm-шаблона не найдены",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибка при обработке запроса",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    )
            }
    )
    @GetMapping("/version/{id}")
    public ResponseEntity<Resource> getVmTemplateSchemesByVersionId(@PathVariable Integer id) throws IOException {
        VmTemplateSchemesGetDto vmTemplateSchemesDto = vmTemplateSchemaService.getVmTemplateSchemesByVersionId(id);
        VmTemplateSchemaFileDto basicSchemaFile = vmTemplateSchemesDto.basicSchema();
        VmTemplateSchemaFileDto nestedSchemaFile = vmTemplateSchemesDto.nestedSchema();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            addFileToZip(zos, basicSchemaFile);
            addFileToZip(zos, nestedSchemaFile);
        }

        ByteArrayResource zipResource = new ByteArrayResource(baos.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"vm-template-schemes.zip\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(zipResource.contentLength())
                .body(zipResource);
    }

    private void addFileToZip(ZipOutputStream zos, VmTemplateSchemaFileDto file) throws IOException {
        ZipEntry entry = new ZipEntry(file.name());
        zos.putNextEntry(entry);
        zos.write(file.fileContent());
        zos.closeEntry();
    }

    @Operation(
            summary = "Сохранение схем vm-шаблона (базовая и вложенная)",
            description = "Принимает файлы базовой и вложенной схемы, а также версию и сохраняет в базу данных",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Схемы vm-шаблона успешно сохранены",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(type = "array", implementation = VmTemplateSchemaResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректный файл. Пожалуйста, загрузите файл в формате xsd.",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
                    @ApiResponse(responseCode = "500", description = "Ошибка при обработке файла",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(type = "string", example = "Произошла ошибка при сохранении файла")))
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<VmTemplateSchemaResponseDto>> saveVmTemplateSchemes(
            @RequestParam Integer versionId,
            @Parameter(description = "Базовая схема vm-шаблона", required = true)
            @RequestPart MultipartFile basicSchema,
            @Parameter(description = "Вложенная схема vm-шаблона", required = true)
            @RequestPart MultipartFile nestedSchema) throws IOException {
        VmTemplateSchemesCreateDto request = new VmTemplateSchemesCreateDto(versionId, basicSchema, nestedSchema);
        List<VmTemplateSchemaDto> dtoList = vmTemplateSchemaService.createVmTemplateSchemas(request);
        List<VmTemplateSchemaResponseDto> response = vmTemplateSchemaMapper.toResponseList(dtoList);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Получить схему vm-шаблона по id версии и типа схемы",
            description = "Позовляет получить файл схемы vm-шаблона по id версии и типа схемы (без дополнительной информации) для дальнейшего скачивания",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Файл успешно загружен",
                            content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                    schema = @Schema(type = "string", format = "binary"))),
                    @ApiResponse(responseCode = "404", description = "Схема не найдена",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE))
            }
    )
    @GetMapping("/schema")
    public ResponseEntity<InputStreamResource> getVmTemplateByVersionAndType(@RequestParam Integer versionId, @RequestParam Integer typeId) {
        VmTemplateSchemaFileDto response = vmTemplateSchemaService.getVmTemplateFileByVersionAndTypeId(versionId, typeId);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(response.fileContent());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + URLEncoder.encode(response.name(), StandardCharsets.UTF_8) + "\"")
                .contentLength(response.fileContent().length)
                .body(new InputStreamResource(byteArrayInputStream));
    }

    @Operation(
            summary = "Получить схему vm-шаблона по id",
            description = "Возвращает схему vm-шаблона по ее id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор схемы vm-шаблона",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Схема vm-шаблона найдена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmTemplateSchemaResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Схема vm-шаблона не найдена")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<VmTemplateSchemaResponseDto> getVmTemplateSchemaById(@PathVariable Integer id) {
        VmTemplateSchemaDto dto = vmTemplateSchemaService.getVmTemplateSchemaById(id);
        VmTemplateSchemaResponseDto response = vmTemplateSchemaMapper.toControllerDto(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Получение списка схем vm-шаблонов",
            description = "Возвращает список схем vm-шаблонов.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешное получение списка схема vm-шаблонов",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = VmTemplateSchemaResponseDto.class)))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<VmTemplateSchemaResponseDto>> getVmTemplateSchemes() {
        List<VmTemplateSchemaDto> dtoList = vmTemplateSchemaService.getVmTemplateSchemas();
        List<VmTemplateSchemaResponseDto> response = vmTemplateSchemaMapper.toResponseList(dtoList);
        return response.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Обновить схему vm-шаблона по Id",
            description = "Обновляет схему vm-шаблона по его Id, включая новый файл схемы и параметры версии. " +
                    "Метод принимает файл, который заменяет текущую схему, и Id версии для ассоциации с обновленной схемой.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Идентификатор схемы vm-шаблона для обновления",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
                    ),
                    @Parameter(
                            name = "versionId",
                            description = "Идентификатор версии схемы vm-шаблона",
                            required = true,
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "integer", example = "2")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Схема vm-шаблона успешно обновлена",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VmTemplateSchemaResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные данные в запросе или файл",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Схема vm-шаблона не найдена",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибка при обработке запроса",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    )
            }
    )
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VmTemplateSchemaResponseDto> updateVmTemplateSchemaById(
            @PathVariable Integer id,
            @RequestParam Integer versionId,
            @RequestPart MultipartFile file
    ) throws IOException {
        VmTemplateSchemaUpdateDto vmTemplateSchemaUpdateDto = new VmTemplateSchemaUpdateDto(versionId, file);
        VmTemplateSchemaDto dto = vmTemplateSchemaService.updateVmTemplateSchema(id, vmTemplateSchemaUpdateDto);
        VmTemplateSchemaResponseDto response = vmTemplateSchemaMapper.toControllerDto(dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Operation(
            summary = "Удалить схему vm-шаблона",
            description = "Удаляет схему vm-шаблона по id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор схемы vm-шаблона для удаления",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Схема vm-шаблона удален"),
                    @ApiResponse(responseCode = "404", description = "Схема vm-шаблона не найдена")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVmTemplateSchema(@PathVariable Integer id) {
        vmTemplateSchemaService.deleteVmTemplateSchema(id);
        return ResponseEntity.noContent().build();
    }
}
