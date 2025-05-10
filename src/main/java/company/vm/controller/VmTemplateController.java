package company.vm.controller;


import company.vm.dto.controller.VmTemplateRequestDto;
import company.vm.dto.controller.VmTemplateResponseDto;
import company.vm.dto.service.template.VmTemplateDto;
import company.vm.dto.service.template.VmTemplateFileDto;
import company.vm.dto.service.template.VmTemplateServiceDto;
import company.vm.mapper.VmTemplateMapper;
import company.vm.service.VmTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@RestController
@RequestMapping("/api/v1/vm-templates")
@Tag(
        name = "Vm-шаблоны",
        description = "API для создания vm-шаблонов в формате XML или PDF"
)
public class VmTemplateController {

    private final VmTemplateService vmTemplateService;
    private final VmTemplateMapper vmTemplateMapper;

    public VmTemplateController(VmTemplateService vmTemplateService,
                                VmTemplateMapper vmTemplateMapper
    ) {
        this.vmTemplateService = vmTemplateService;
        this.vmTemplateMapper = vmTemplateMapper;
    }

    @Operation(
            summary = "Генерация XML-шаблона",
            description = "Принимает файл в формате XML и генерирует vm-шаблон",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Xml-шаблон успешно сгенерирован",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(type = "string", example = "File uploaded successfully"))),
                    @ApiResponse(responseCode = "400", description = "Некорректный файл. Пожалуйста, загрузите файл в формате XML.",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
                    @ApiResponse(responseCode = "500", description = "Ошибка при обработке файла",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(type = "string", example = "Error processing file"))),
            }
    )
    @PostMapping(value = "/xml-generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<InputStreamResource> generateXmlTemplate(
            @Parameter(description = "Файл, содержащий данные для генерации XML-шаблона", required = true)
            @RequestPart("file") MultipartFile file) {
        try {
            VmTemplateFileDto vmTemplateFile = vmTemplateService.generateXmlTemplate(file);
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(vmTemplateFile.fileContent()));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + vmTemplateFile.fileName() + "\"")
                    .contentType(MediaType.valueOf(vmTemplateFile.contentType()))
                    .contentLength(vmTemplateFile.fileContent().length)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
            summary = "Генерация PDF-шаблона",
            description = "Принимает файл в формате PDF и генерирует vm-шаблон",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pdf-шаблон успешно сгенерирован",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(type = "string", example = "File uploaded successfully"))),
                    @ApiResponse(responseCode = "400", description = "Некорректный файл. Пожалуйста, загрузите файл в формате PDF.",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
                    @ApiResponse(responseCode = "500", description = "Ошибка при обработке файла",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(type = "string", example = "Error processing file"))),
            }
    )
    @PostMapping(value = "/pdf-generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<InputStreamResource> generatePdfTemplate(
            @Parameter(description = "Номер услуги", required = true)
            @RequestParam String serviceNumber,
            @Parameter(description = "Файл, содержащий данные для генерации PDF-шаблона", required = true)
            @RequestPart("file") MultipartFile file) {
        try {
            VmTemplateFileDto vmTemplateFile = vmTemplateService.generatePdfTemplate(serviceNumber, file);
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(vmTemplateFile.fileContent()));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + vmTemplateFile.fileName() + "\"")
                    .contentType(MediaType.valueOf(vmTemplateFile.contentType()))
                    .contentLength(vmTemplateFile.fileContent().length)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
            summary = "Сохранение vm-шаблона",
            description = "Принимает файл и параметры с его описанием и сохраняет в базу данных",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Vm-шаблон успешно сохранен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VmTemplateResponseDto.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Некорректный файл. Пожалуйста, загрузите файл в формате vm.",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(type = "string", example = "Некорректный файл. Пожалуйста, загрузите файл в формате vm."))),
                    @ApiResponse(responseCode = "500", description = "Ошибка при обработке файла",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(type = "string", example = "Произошла ошибка при сохранении файла")))
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VmTemplateResponseDto> saveVmTemplate(
            @Parameter(description = "Параметры шаблона", required = true)
            @ModelAttribute VmTemplateRequestDto request,
            @Parameter(description = "Файл шаблона", required = true)
            @RequestPart MultipartFile file) throws IOException {
        VmTemplateServiceDto templateService = vmTemplateMapper.toServiceDto(request, file);
        VmTemplateDto dto = vmTemplateService.saveTemplate(templateService);
        VmTemplateResponseDto response = vmTemplateMapper.toControllerDto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Скачать vm-шаблон по Id",
            description = "Позовляет получить файл по id (без дополнительной информации) для дальнейшего скачивания",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор файла в базе данных",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Файл успешно загружен",
                            content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                    schema = @Schema(type = "string", format = "binary"))),
                    @ApiResponse(responseCode = "404", description = "Не найден",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> downloadVmTemplateById(@PathVariable Integer id) {
        VmTemplateFileDto response = vmTemplateService.downloadTemplateById(id);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(response.fileContent());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + URLEncoder.encode(response.fileName(), StandardCharsets.UTF_8) + "\"")
                .contentLength(response.fileContent().length)
                .contentType(MediaType.parseMediaType(response.contentType()))
                .body(new InputStreamResource(byteArrayInputStream));
    }

    @Operation(
            summary = "Получение списка vm-шаблонов (превью)",
            description = "Возвращает постраничный список vm-шаблонов с возможностью пагинации и сортировки.",
            parameters = {
                    @Parameter(name = "page", description = "Номер страницы (начиная с 0)", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")),
                    @Parameter(name = "size", description = "Размер страницы", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "20")),
                    @Parameter(name = "sort", description = "Сортировка в формате: поле,asc|desc. Можно указать несколько.", in = ParameterIn.QUERY, array = @ArraySchema(schema = @Schema(type = "string")))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешное получение списка шаблонов",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = VmTemplateResponseDto.class)))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<Page<VmTemplateResponseDto>> getPreviewTemplates(@PageableDefault Pageable pageRequest) {
        Page<VmTemplateDto> page = vmTemplateService.getPreviewTemplates(pageRequest);
        Page<VmTemplateResponseDto> response = vmTemplateMapper.toResponsePage(page);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Обновить vm-шаблон по id",
            description = "Обновляет данные vm-шаблона по его id, включая файл и параметры шаблона",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор vm-шаблона для обновления",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = VmTemplateRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Vm-шаблон успешно обновлён",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VmTemplateResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные данные в запросе или файл",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Vm-шаблон не найден",
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
    public ResponseEntity<VmTemplateResponseDto> updateVmTemplateById(
            @PathVariable Integer id,
            @ModelAttribute VmTemplateRequestDto request,
            @RequestPart MultipartFile file
    ) throws IOException {
        VmTemplateServiceDto templateService = vmTemplateMapper.toServiceDto(request, file);
        VmTemplateDto dto = vmTemplateService.updateTemplate(id, templateService);
        VmTemplateResponseDto response = vmTemplateMapper.toControllerDto(dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Operation(
            summary = "Удалить vm-шаблон",
            description = "Удаляет vm-шаблон по id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор vm-шаблона для удаления",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Vm-шаблон удален"),
                    @ApiResponse(responseCode = "404", description = "Vm-шаблон не найден")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVmTemplate(@PathVariable Integer id) {
        vmTemplateService.deleteVmTemplate(id);
        return ResponseEntity.noContent().build();
    }

}
