package company.vm.controller;


import company.vm.dto.controller.DateTimeFormatOptionRequestDto;
import company.vm.dto.controller.DateTimeFormatOptionResponseDto;
import company.vm.dto.service.spr.DateTimeFormatOptionDto;
import company.vm.dto.service.spr.DateTimeFormatOptionIdDto;
import company.vm.mapper.DateTimeFormatOptionMapper;
import company.vm.service.DateTimeFormatOptionService;
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
@RequestMapping("/api/v1/date-time-formats")
@Tag(
        name = "Форматы даты и времени vm-шаблона",
        description = "API для управления форматами даты и времени"
)
public class DateTimeFormatOptionController {
    private final DateTimeFormatOptionService dateTimeFormatOptionService;
    private final DateTimeFormatOptionMapper dateFormatOptionMapper;
    private final DateTimeFormatOptionMapper dateTimeFormatOptionMapper;

    public DateTimeFormatOptionController(DateTimeFormatOptionService dateTimeFormatOptionService, DateTimeFormatOptionMapper dateFormatOptionMapper, DateTimeFormatOptionMapper dateTimeFormatOptionMapper) {
        this.dateTimeFormatOptionService = dateTimeFormatOptionService;
        this.dateFormatOptionMapper = dateFormatOptionMapper;
        this.dateTimeFormatOptionMapper = dateTimeFormatOptionMapper;
    }

    @Operation(
            summary = "Создать новый формат даты и времени",
            description = "Создает новый формат даты и врмени и возвращает его данные.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = DateTimeFormatOptionRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Новый формат даты и времени успешно создан",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DateTimeFormatOptionResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            }
    )
    @PostMapping
    public ResponseEntity<DateTimeFormatOptionResponseDto> createDateTimeFormatOption(@RequestBody DateTimeFormatOptionRequestDto request) {
        DateTimeFormatOptionDto dateTimeFormatOptionDto = dateFormatOptionMapper.toServiceDto(request);
        DateTimeFormatOptionIdDto createdDateTimeFormatOption = dateTimeFormatOptionService.createDateTimeFormatOption(dateTimeFormatOptionDto);
        DateTimeFormatOptionResponseDto response = dateFormatOptionMapper.toControllerDto(createdDateTimeFormatOption);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Получить формат даты и времени по id",
            description = "Возвращает формат даты и времени по id. Если формат даты и времени не найден, будет возвращен статус 404.",
            parameters = @Parameter(
                    name = "id",
                    description = "Уникальный идентификатор формата даты и времени",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Формат даты и врмени успешно найден",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DateTimeFormatOptionResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Формат даты и времени не найден")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<DateTimeFormatOptionResponseDto> getDateTimeFormatOptionById(@PathVariable Integer id) {
        DateTimeFormatOptionIdDto dateTimeFormatOptionDto = dateTimeFormatOptionService.getDateTimeFormatOptionById(id);
        DateTimeFormatOptionResponseDto response = dateTimeFormatOptionMapper.toControllerDto(dateTimeFormatOptionDto);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Обновить формат даты и времени по id",
            description = "Обновляет формат даты и времени по id. Возвращает обновленные данные формата даты и времени.",
            parameters = @Parameter(
                    name = "id",
                    description = "Уникальный идентификатор формата даты и времени",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = DateTimeFormatOptionRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Формат даты и времени успешно обновлен",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DateTimeFormatOptionResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Формат даты и времени не найден"),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<DateTimeFormatOptionResponseDto> updateDateTimeFormatOption(@PathVariable Integer id, @RequestBody DateTimeFormatOptionRequestDto request) {
        DateTimeFormatOptionDto dateTimeFormatOptionDto = dateTimeFormatOptionMapper.toServiceDto(request);
        DateTimeFormatOptionIdDto updatedPdfClassName = dateTimeFormatOptionService.updateDateTimeFormatOption(id, dateTimeFormatOptionDto);
        DateTimeFormatOptionResponseDto response = dateFormatOptionMapper.toControllerDto(updatedPdfClassName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Operation(
            summary = "Получить форматы даты и времени",
            description = "Возвращает список всех форматов даты и времени. Если список пуст, возвращается статус 204.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список форматов даты и времени успешно возвращен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DateTimeFormatOptionResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "204", description = "Список форматов даты и времени пуст")
            }
    )
    @GetMapping
    public ResponseEntity<List<DateTimeFormatOptionResponseDto>> getDateTimeFormatOptions() {
        List<DateTimeFormatOptionIdDto> dateTimeFormatOptions = dateTimeFormatOptionService.getDateTimeFormatOptions();
        List<DateTimeFormatOptionResponseDto> response = dateFormatOptionMapper.toControllerDtoList(dateTimeFormatOptions);
        return response.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Удалить формат даты и времени",
            description = "Удаляет формат даты и времени по id. Если формат даты и времени не найден, возвращается статус 404.",
            parameters = @Parameter(
                    name = "id",
                    description = "Уникальный идентификатор формата даты и времени",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Формат даты и времени успешно удален"),
                    @ApiResponse(responseCode = "404", description = "Формат даты и времени не найден")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDateTimeFormatOption(@PathVariable Integer id) {
        dateTimeFormatOptionService.deleteDateTimeFormatOption(id);
        return ResponseEntity.noContent().build();
    }
}
