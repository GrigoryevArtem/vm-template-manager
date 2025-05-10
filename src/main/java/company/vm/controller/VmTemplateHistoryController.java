package company.vm.controller;


import company.vm.dto.controller.VmTemplateHistoryRequestDto;
import company.vm.dto.controller.VmTemplateHistoryResponseDto;
import company.vm.dto.service.template.VmTemplateHistoryDto;
import company.vm.dto.service.template.VmTemplateHistoryFileDto;
import company.vm.dto.service.template.VmTemplateHistoryServiceDto;
import company.vm.mapper.VmTemplateHistoryMapper;
import company.vm.service.VmTemplateHistoryService;
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
@RequestMapping("/api/v1/vm-template-histories")
@Tag(
        name = "История сборки vm-шаблонов",
        description = "API для создания vm-шаблонов в формате XML или PDF"
)
public class VmTemplateHistoryController {

    private final VmTemplateHistoryService vmTemplateHistoryService;
    private final VmTemplateHistoryMapper vmTemplateHistoryMapper;

    public VmTemplateHistoryController(VmTemplateHistoryService vmTemplateHistoryService,
                                       VmTemplateHistoryMapper vmTemplateHistoryMapper
    ) {
        this.vmTemplateHistoryService = vmTemplateHistoryService;
        this.vmTemplateHistoryMapper = vmTemplateHistoryMapper;
    }

    @Operation(
            summary = "Сохранение истории сборки vm-шаблона",
            description = "Принимает файл и параметры с его описанием истории сборки и сохраняет в базу данных",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Vm-шаблон успешно сохранен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VmTemplateHistoryResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректный файл",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибка при обработке файла",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(type = "string", example = "Произошла ошибка при сохранении файла")
                            )
                    )
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VmTemplateHistoryResponseDto> saveVmTemplateHistory(
            @Parameter(description = "Параметры истории сборки шаблона", required = true)
            @ModelAttribute VmTemplateHistoryRequestDto request,
            @Parameter(description = "Файл истории сборки шаблона", required = true)
            @RequestPart MultipartFile file) throws IOException {
        VmTemplateHistoryServiceDto templateHistoryServiceDto = vmTemplateHistoryMapper.toServiceDto(request, file);
        VmTemplateHistoryDto dto = vmTemplateHistoryService.saveVmTemplateHistory(templateHistoryServiceDto);
        VmTemplateHistoryResponseDto response = vmTemplateHistoryMapper.toControllerDto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Скачать историю сборки vm-шаблона по Id",
            description = "Позовляет получить историю сборки vm-шаблона по id (без дополнительной информации) для дальнейшего скачивания",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор файла в базе данных",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Инструкция успешно найдена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(type = "string", format = "binary")
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Файл с историей сборки не найден")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> downloadVmTemplateHistoryById(@PathVariable Integer id) {
        VmTemplateHistoryFileDto response = vmTemplateHistoryService.downloadVmTemplateHistoryById(id);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(response.fileContent());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + URLEncoder.encode(response.fileName(), StandardCharsets.UTF_8) + "\"")
                .contentLength(response.fileContent().length)
                .contentType(MediaType.parseMediaType(response.contentType()))
                .body(new InputStreamResource(byteArrayInputStream));
    }

    @Operation(
            summary = "Получение списка истории сборки vm-шаблонов (превью)",
            description = "Возвращает постраничный список историй сборки vm-шаблонов с возможностью пагинации и сортировки.",
            parameters = {
                    @Parameter(name = "page", description = "Номер страницы (начиная с 0)", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")),
                    @Parameter(name = "size", description = "Размер страницы", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "20")),
                    @Parameter(name = "sort", description = "Сортировка в формате: поле,asc|desc. Можно указать несколько.", in = ParameterIn.QUERY, array = @ArraySchema(schema = @Schema(type = "string")))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешное получение списка историй сборки шаблонов",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = VmTemplateHistoryResponseDto.class)))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<Page<VmTemplateHistoryResponseDto>> getPreviewTemplateHistories(@PageableDefault Pageable pageRequest) {
        Page<VmTemplateHistoryDto> page = vmTemplateHistoryService.getPreviewVmTemplateHistories(pageRequest);
        Page<VmTemplateHistoryResponseDto> response = vmTemplateHistoryMapper.toResponsePage(page);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Обновить историю сборки vm-шаблона по id",
            description = "Обновляет данные истории сборки vm-шаблона по его id, включая файл и параметры шаблона",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор сборки vm-шаблона для обновления",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = VmTemplateHistoryRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "История сборки vm-шаблона успешно обновлён",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VmTemplateHistoryResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные данные в запросе или файл",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "История сборки vm-шаблона не найден",
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
    public ResponseEntity<VmTemplateHistoryResponseDto> updateVmTemplateHistoryById(
            @PathVariable Integer id,
            @ModelAttribute VmTemplateHistoryRequestDto request,
            @RequestPart MultipartFile file
    ) throws IOException {
        VmTemplateHistoryServiceDto templateServiceHistoryDto = vmTemplateHistoryMapper.toServiceDto(request, file);
        VmTemplateHistoryDto dto = vmTemplateHistoryService.updateVmTemplateHistory(id, templateServiceHistoryDto);
        VmTemplateHistoryResponseDto responseDto = vmTemplateHistoryMapper.toControllerDto(dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDto);
    }

    @Operation(
            summary = "Удалить историю сборки vm-шаблона",
            description = "Удаляет историю сборки vm-шаблона по id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор истории сборки vm-шаблона для удаления",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "История сборки Vm-шаблона удалена"),
                    @ApiResponse(responseCode = "404", description = "История сборки Vm-шаблона не найден")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVmTemplateHistory(@PathVariable Integer id) {
        vmTemplateHistoryService.deleteVmTemplateHistory(id);
        return ResponseEntity.noContent().build();
    }
}
