package company.vm.controller;


import company.vm.dto.controller.VmGlobalVariableRequestDto;
import company.vm.dto.controller.VmGlobalVariableResponseDto;
import company.vm.dto.service.spr.VmGlobalVariableDto;
import company.vm.dto.service.spr.VmGlobalVariableIdDto;
import company.vm.mapper.VmGlobalVariableMapper;
import company.vm.service.VmGlobalVariableService;
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
@RequestMapping("/api/v1/vm-global-variables")
@Tag(
        name = "Глобальные переменные vm-шаблона",
        description = "API для управления глобальными переменными"
)
public class VmGlobalVariableController {
    private final VmGlobalVariableService vmGlobalVariableService;
    private final VmGlobalVariableMapper vmGlobalVariableMapper;

    public VmGlobalVariableController(VmGlobalVariableService vmGlobalVariableService,
                                      VmGlobalVariableMapper vmGlobalVariableMapper
    ) {
        this.vmGlobalVariableService = vmGlobalVariableService;
        this.vmGlobalVariableMapper = vmGlobalVariableMapper;
    }

    @Operation(
            summary = "Создать глобальную переменную",
            description = "Создает новую глобальную переменную и возвращает её данные",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = VmGlobalVariableRequestDto.class)
                    )

            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Глобальная переменная создана",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmGlobalVariableResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные данные в запросе")
            }
    )
    @PostMapping
    public ResponseEntity<VmGlobalVariableResponseDto> createGlobalVariable(@RequestBody VmGlobalVariableRequestDto request) {
        VmGlobalVariableDto vmGlobalVariableDto = vmGlobalVariableMapper.toServiceDto(request);
        VmGlobalVariableIdDto createdVmGlobalVariable = vmGlobalVariableService.createGlobalVariable(vmGlobalVariableDto);
        VmGlobalVariableResponseDto response = vmGlobalVariableMapper.toControllerDto(createdVmGlobalVariable);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Получить глобальную переменную по id",
            description = "Возвращает данные глобальной переменной по её id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор глобальной переменной",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Глобальная переменная найдена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmGlobalVariableResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Глобальная переменная не найдена")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<VmGlobalVariableResponseDto> getGlobalVariableById(@PathVariable Integer id) {
        VmGlobalVariableIdDto vmGlobalVariable = vmGlobalVariableService.getGlobalVariableById(id);
        VmGlobalVariableResponseDto response = vmGlobalVariableMapper.toControllerDto(vmGlobalVariable);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Обновить глобальную переменную по id",
            description = "Обновляет данные глобальной переменной по её id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор глобальной переменной для обновления",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = VmGlobalVariableRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "202", description = "Глобальная переменная обновлена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmGlobalVariableResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные данные в запросе"),
                    @ApiResponse(responseCode = "404", description = "Глобальная переменная не найдена")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<VmGlobalVariableResponseDto> updateGlobalVariable(@PathVariable Integer id, @RequestBody VmGlobalVariableRequestDto request) {
        VmGlobalVariableDto vmGlobalVariableDto = vmGlobalVariableMapper.toServiceDto(request);
        VmGlobalVariableIdDto updatedVmGlobalVariable = vmGlobalVariableService.updateGlobalVariable(id, vmGlobalVariableDto);
        VmGlobalVariableResponseDto response = vmGlobalVariableMapper.toControllerDto(updatedVmGlobalVariable);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Operation(
            summary = "Получить список глобальных переменных",
            description = "Возвращает список всех глобальных переменных",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список глобальных переменных возвращён",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmGlobalVariableResponseDto.class))),
                    @ApiResponse(responseCode = "204", description = "Список глобальных переменных пуст")
            }
    )
    @GetMapping
    public ResponseEntity<List<VmGlobalVariableResponseDto>> getGlobalVariables() {
        List<VmGlobalVariableIdDto> vmGlobalVariables = vmGlobalVariableService.getGlobalVariables();
        List<VmGlobalVariableResponseDto> response = vmGlobalVariableMapper.toControllerDtoList(vmGlobalVariables);
        return response.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Удалить глобальную переменную",
            description = "Удаляет глобальную переменную по её id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор глобальной переменной для удаления",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Глобальная переменная удалена"),
                    @ApiResponse(responseCode = "404", description = "Глобальная переменная не найдена")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGlobalVariable(@PathVariable Integer id) {
        vmGlobalVariableService.deleteGlobalVariable(id);
        return ResponseEntity.noContent().build();
    }
}
