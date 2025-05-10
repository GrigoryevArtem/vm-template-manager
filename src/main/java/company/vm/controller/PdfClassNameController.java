package company.vm.controller;


import company.vm.dto.controller.PdfClassNameRequestDto;
import company.vm.dto.controller.PdfClassNameResponseDto;
import company.vm.dto.service.spr.PdfClassNameDto;
import company.vm.dto.service.spr.PdfClassNameIdDto;
import company.vm.mapper.PdfClassNameMapper;
import company.vm.service.PdfClassNameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/pdf-class-names")
@Tag(
        name = "Инструкции для pdf vm-шаблона",
        description = "API для управления инструкциями для pdf (className)"
)
public class PdfClassNameController {

    private final PdfClassNameService pdfClassNameService;
    private final PdfClassNameMapper pdfClassNameMapper;

    public PdfClassNameController(PdfClassNameService pdfClassNameService,
                                  PdfClassNameMapper pdfClassNameMapper
    ) {
        this.pdfClassNameService = pdfClassNameService;
        this.pdfClassNameMapper = pdfClassNameMapper;
    }

    @Operation(
            summary = "Создать инструкцию pdf",
            description = "Создает новую инструкцию pdf и возвращает её данные. Инструкция создается на основе переданных данных и сохраняется в базе.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = PdfClassNameRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Инструкция успешно создана",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PdfClassNameResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            }
    )
    @PostMapping
    public ResponseEntity<PdfClassNameResponseDto> createPdfClassName(@RequestBody PdfClassNameRequestDto request) {
        PdfClassNameDto pdfClassNameDto = pdfClassNameMapper.toServiceDto(request);
        PdfClassNameIdDto createdPdfClassName = pdfClassNameService.createPdfClassName(pdfClassNameDto);
        PdfClassNameResponseDto response = pdfClassNameMapper.toControllerDto(createdPdfClassName);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Получить инструкцию pdf по id",
            description = "Возвращает данные инструкции pdf по её id. Если инструкция не найдена, будет возвращен статус 404.",
            parameters = @Parameter(
                    name = "id",
                    description = "Уникальный идентификатор инструкции",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Инструкция успешно найдена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PdfClassNameResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Инструкция не найдена")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PdfClassNameResponseDto> getPdfClassNameById(@PathVariable Integer id) {
        PdfClassNameIdDto foundPdfClassName = pdfClassNameService.getPdfClassNameById(id);
        PdfClassNameResponseDto response = pdfClassNameMapper.toControllerDto(foundPdfClassName);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Обновить инструкцию pdf по id",
            description = "Обновляет инструкцию pdf по её id. Возвращает обновленные данные инструкции.",
            parameters = @Parameter(
                    name = "id",
                    description = "Уникальный идентификатор инструкции",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = PdfClassNameRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Инструкция успешно обновлена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PdfClassNameResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Инструкция не найдена"),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<PdfClassNameResponseDto> updatePdfClassName(@PathVariable Integer id, @RequestBody PdfClassNameRequestDto request) {
        PdfClassNameDto pdfClassNameDto = pdfClassNameMapper.toServiceDto(request);
        PdfClassNameIdDto updatePdfClassName = pdfClassNameService.updatePdfClassName(id, pdfClassNameDto);
        PdfClassNameResponseDto response = pdfClassNameMapper.toControllerDto(updatePdfClassName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Operation(
            summary = "Получить список инструкций pdf",
            description = "Возвращает список всех инструкций pdf. Если список пуст, возвращается статус 204.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список инструкций успешно возвращен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PdfClassNameResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "204", description = "Список инструкций пуст")
            }
    )
    @GetMapping
    public ResponseEntity<List<PdfClassNameResponseDto>> getPdfClassNames() {
        List<PdfClassNameIdDto> pdfClassNames = pdfClassNameService.getPdfClassNames();
        List<PdfClassNameResponseDto> response = pdfClassNameMapper.toControllerDtoList(pdfClassNames);
        return response.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Удалить инструкцию pdf",
            description = "Удаляет инструкцию pdf по её id. Если инструкция не найдена, возвращается статус 404.",
            parameters = @Parameter(
                    name = "id",
                    description = "Уникальный идентификатор инструкции",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Инструкция успешно удалена"),
                    @ApiResponse(responseCode = "404", description = "Инструкция не найдена")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePdfClassName(@PathVariable Integer id) {
        pdfClassNameService.deletePdfClassName(id);
        return ResponseEntity.noContent().build();
    }
}
