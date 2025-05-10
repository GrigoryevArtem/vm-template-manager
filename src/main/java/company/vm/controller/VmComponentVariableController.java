package company.vm.controller;


import company.vm.dto.controller.VmComponentVariableRequestDto;
import company.vm.dto.controller.VmComponentVariableResponseDto;
import company.vm.dto.service.spr.VmComponentVariableDto;
import company.vm.dto.service.spr.VmComponentVariableIdDto;
import company.vm.mapper.VmComponentVariableMapper;
import company.vm.service.VmComponentVariableService;
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
@RequestMapping("/api/v1/vm-component-variables")
@Tag(
        name = "Переменные компонентов vm-шаблона",
        description = "API для управления переменными компонента vm-шаблона"
)
public class VmComponentVariableController {

    private final VmComponentVariableService vmComponentVariableService;
    private final VmComponentVariableMapper vmComponentVariableMapper;

    public VmComponentVariableController(VmComponentVariableService vmComponentVariableService,
                                         VmComponentVariableMapper vmComponentVariableMapper
    ) {
        this.vmComponentVariableService = vmComponentVariableService;
        this.vmComponentVariableMapper = vmComponentVariableMapper;
    }

    @Operation(
            summary = "Создать переменную компонента vm-шаблона",
            description = "Создает новую переменную компонента vm-шаблона",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = VmComponentVariableRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Новая переменная компонента успешно создана",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VmComponentVariableResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            }
    )
    @PostMapping
    public ResponseEntity<VmComponentVariableResponseDto> createVmComponentVariable(@RequestBody VmComponentVariableRequestDto request) {
        VmComponentVariableDto vmComponentVariableDto = vmComponentVariableMapper.toServiceDto(request);
        VmComponentVariableIdDto createdVmComponentVariable = vmComponentVariableService.createVmComponentVariable(vmComponentVariableDto);
        VmComponentVariableResponseDto response = vmComponentVariableMapper.toControllerDto(createdVmComponentVariable);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Получить переменную компонента vm-шаблона по id",
            description = "Возвращает данные переменной компонента vm-шаблона по id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор переменной компонента vm-шаблона",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Переменная компонента vm-шаблона найдена",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = VmComponentVariableResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Переменная компонента vm-шаблона не найдена"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<VmComponentVariableResponseDto> getVmComponentVariableById(@PathVariable Integer id) {
        VmComponentVariableIdDto vmComponentVariableDto = vmComponentVariableService.getVmComponentVariableById(id);
        VmComponentVariableResponseDto response = vmComponentVariableMapper.toControllerDto(vmComponentVariableDto);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Обновить переменную компонента vm-шаблона по id",
            description = "Обновляет данные переменной компонента vm-шаблона по её id",
            parameters = @Parameter(
                    name = "id",
                    description = "Уникальный идентификатор переменной компонента vm-шаблона",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = VmComponentVariableRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Переменная компонента vm-шаблона успешно обновлена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VmComponentVariableResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Перменная компонента vm-шаблона не найдена"),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<VmComponentVariableResponseDto> updateVmComponentVariable(@PathVariable Integer id, @RequestBody VmComponentVariableRequestDto request) {
        VmComponentVariableDto vmComponentVariableDto = vmComponentVariableMapper.toServiceDto(request);
        VmComponentVariableIdDto updatedVmComponentVariable = vmComponentVariableService.updateVmComponentVariable(id, vmComponentVariableDto);
        VmComponentVariableResponseDto response = vmComponentVariableMapper.toControllerDto(updatedVmComponentVariable);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Operation(
            summary = "Получить список всех переменных компонента vm-шаблона",
            description = "Возвращает список всех переменных компонента vm-шаблона",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список переменных возвращен",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmComponentVariableResponseDto.class))),
                    @ApiResponse(responseCode = "204", description = "Список переменных пуст")
            }
    )
    @GetMapping
    public ResponseEntity<List<VmComponentVariableResponseDto>> getVmComponentVariables() {
        List<VmComponentVariableIdDto> vmComponentVariables = vmComponentVariableService.getVmComponentVariables();
        List<VmComponentVariableResponseDto> response = vmComponentVariableMapper.toControllerDtoList(vmComponentVariables);
        return response.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Удалить переменную компонента vm-шаблона",
            description = "Удаляет переменную компонента vm-шаблона по id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор переменной компонента vm-шаблона",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Переменная компонента vm-шаблона успешно удалена"),
                    @ApiResponse(responseCode = "404", description = "Переменная компонента vm-шаблона не найдена")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVmComponentVariable(@PathVariable Integer id) {
        vmComponentVariableService.deleteVmComponentVariable(id);
        return ResponseEntity.noContent().build();
    }
}
