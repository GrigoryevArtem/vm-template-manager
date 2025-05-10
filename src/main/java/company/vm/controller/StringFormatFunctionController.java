package company.vm.controller;


import company.vm.dto.controller.StringFormatFunctionRequestDto;
import company.vm.dto.controller.StringFormatFunctionResponseDto;
import company.vm.dto.service.spr.StringFormatFunctionDto;
import company.vm.dto.service.spr.StringFormatFunctionIdDto;
import company.vm.mapper.StringFormatFunctionMapper;
import company.vm.service.StringFormatFunctionService;
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
@RequestMapping("/api/v1/string-format-functions")
@Tag(
        name = "Функции форматирования строк vm-шаблона",
        description = "API для управления функциями форматирования строк"
)
public class StringFormatFunctionController {

    private final StringFormatFunctionService stringFormatFunctionService;
    private final StringFormatFunctionMapper stringFormatFunctionMapper;

    public StringFormatFunctionController(StringFormatFunctionService stringFormatFunctionService,
                                          StringFormatFunctionMapper stringFormatFunctionMapper
    ) {
        this.stringFormatFunctionService = stringFormatFunctionService;
        this.stringFormatFunctionMapper = stringFormatFunctionMapper;
    }

    @Operation(
            summary = "Создать функцию форматирования строк",
            description = "Создает новую функцию форматирования строк и возвращает её данные",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = StringFormatFunctionRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Функция форматирования строк успешно создана",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = StringFormatFunctionResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            }
    )
    @PostMapping
    public ResponseEntity<StringFormatFunctionResponseDto> createStringFormatFunction(@RequestBody StringFormatFunctionRequestDto request) {
        StringFormatFunctionDto dto = stringFormatFunctionMapper.toServiceDto(request);
        StringFormatFunctionIdDto createdStringFormatFunction = stringFormatFunctionService.createStringFormatFunction(dto);
        StringFormatFunctionResponseDto response = stringFormatFunctionMapper.toControllerDto(createdStringFormatFunction);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Получить функцию форматирования строк по id",
            description = "Возвращает данные функции форматирования строк по её id",
            parameters = @Parameter(
                    name = "id",
                    description = "Уникальный идентификатор функции форматирования строк",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Функция форматирования строк успешно найдена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = StringFormatFunctionResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Функция форматирования строк не найдена")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<StringFormatFunctionResponseDto> getStringFormatFunctionById(@PathVariable Integer id) {
        StringFormatFunctionIdDto stringFormatFunctionIdDto = stringFormatFunctionService.getStringFormatFunctionById(id);
        StringFormatFunctionResponseDto responseDto = stringFormatFunctionMapper.toControllerDto(stringFormatFunctionIdDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Получить список функций форматирования строк",
            description = "Возвращает список всех функций форматирования строк",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список фукнций форматирования строк успешно возвращен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = StringFormatFunctionResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "204", description = "Список функций форматирования строк пуст")
            }
    )
    @GetMapping
    public ResponseEntity<List<StringFormatFunctionResponseDto>> getAllStringFormatFunctions() {
        List<StringFormatFunctionIdDto> stringFormatFunctions = stringFormatFunctionService.getStringFormatFunctions();
        List<StringFormatFunctionResponseDto> response = stringFormatFunctionMapper.toControllerDtoList(stringFormatFunctions);
        return response.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Обновить функцию форматирования строк по id",
            description = "Обновляет функцию форматирования строк по её id. Возвращает обновленные данные инструкции.",
            parameters = @Parameter(
                    name = "id",
                    description = "Уникальный идентификатор функциия форматирования строк",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = StringFormatFunctionRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Функция форматирования строк успешно обновлена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = StringFormatFunctionResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Функция форматирования строк не найдена"),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<StringFormatFunctionResponseDto> updateStringFormatFunction(@PathVariable Integer id, @RequestBody StringFormatFunctionRequestDto request) {
        StringFormatFunctionDto dto = stringFormatFunctionMapper.toServiceDto(request);
        StringFormatFunctionIdDto updatedStringFormatFunction = stringFormatFunctionService.updateStringFormatFunction(id, dto);
        StringFormatFunctionResponseDto response = stringFormatFunctionMapper.toControllerDto(updatedStringFormatFunction);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Operation(
            summary = "Удалить функцию форматирования строк",
            description = "Удаляет функцию форматирования строк по её id",
            parameters = @Parameter(
                    name = "id",
                    description = "Уникальный идентификатор функции форматирования строк",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Функции форматирования строк успешно удалена"),
                    @ApiResponse(responseCode = "404", description = "Функции форматирования строк не найдена")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStringFormatFunction(@PathVariable Integer id) {
        stringFormatFunctionService.deleteStringFormatFunction(id);
        return ResponseEntity.noContent().build();
    }
}
