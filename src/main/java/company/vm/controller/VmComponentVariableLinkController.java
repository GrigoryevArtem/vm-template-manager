package company.vm.controller;


import company.vm.dto.controller.VmComponentVariableLinkNodeResponseDto;
import company.vm.dto.controller.VmComponentVariableLinkRequestDto;
import company.vm.dto.controller.VmComponentVariableLinkResponseDto;
import company.vm.dto.service.spr.VmComponentVariableLinkDto;
import company.vm.dto.service.spr.VmComponentVariableLinkIdDto;
import company.vm.mapper.VmComponentVariableLinkMapper;
import company.vm.service.VmComponentVariableLinkService;
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
import java.util.Map;


@RestController
@RequestMapping("/api/v1/link-vm-component-variables")
@Tag(
        name = "Иерархические связи переменных компонентов vm-шаблона",
        description = "API для управления иерархическими связями переменных компонента vm-шаблона"
)
public class VmComponentVariableLinkController {

    private final VmComponentVariableLinkService vmComponentVariableLinkService;
    private final VmComponentVariableLinkMapper vmComponentVariableLinkMapper;

    public VmComponentVariableLinkController(VmComponentVariableLinkService vmComponentVariableLinkService,
                                             VmComponentVariableLinkMapper vmComponentVariableLinkMapper
    ) {
        this.vmComponentVariableLinkService = vmComponentVariableLinkService;
        this.vmComponentVariableLinkMapper = vmComponentVariableLinkMapper;
    }

    @Operation(
            summary = "Создать связь переменной компонента с компонентом",
            description = "Создает новую связь переменной компонента с компонентом и возвращает её данные",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = VmComponentVariableLinkRequestDto.class)
                    )

            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Связь переменной компонента с компонентом создана",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmComponentVariableLinkResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные данные в запросе")
            }
    )
    @PostMapping
    public ResponseEntity<VmComponentVariableLinkResponseDto> createVmComponentVariableLink(@RequestBody VmComponentVariableLinkRequestDto request) {
        VmComponentVariableLinkDto vmComponentVariableLinkIdDto = vmComponentVariableLinkMapper.toServiceDto(request);
        VmComponentVariableLinkIdDto vmComponentVariableLinkFullDto = vmComponentVariableLinkService.createVmComponentVariableLink(vmComponentVariableLinkIdDto);
        VmComponentVariableLinkResponseDto response = vmComponentVariableLinkMapper.toControllerDto(vmComponentVariableLinkFullDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Получить связку переменной компонента vm-шаблона по id",
            description = "Возвращает данные связки переменной компонента vm-шаблона по id",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Идентификатор переменной компонента vm-шаблона",
                            required = true,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Связка успешно получена",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VmComponentVariableLinkResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Связка не найдена"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<VmComponentVariableLinkResponseDto> getVmComponentVariableLinkById(@PathVariable Integer id) {
        VmComponentVariableLinkIdDto vmComponentVariableDto = vmComponentVariableLinkService.getVmComponentVariableLinkById(id);
        VmComponentVariableLinkResponseDto response = vmComponentVariableLinkMapper.toControllerDto(vmComponentVariableDto);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Обновить связку переменной компонента vm-шаблона по id",
            description = "Обновляет данные связки переменной vm-шаблона по её id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор связи переменной и компонента для обновления",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = VmComponentVariableLinkRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "202", description = "Связи переменной компонента и компонента обновлена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmComponentVariableLinkResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные данные в запросе"),
                    @ApiResponse(responseCode = "404", description = "Связь переменной компонента и компонента не найдена")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<VmComponentVariableLinkResponseDto> updateVmComponentVariableLink(@PathVariable Integer id, @RequestBody VmComponentVariableLinkRequestDto request) {
        VmComponentVariableLinkDto vmComponentVariableLinkIdDto = vmComponentVariableLinkMapper.toServiceDto(request);
        VmComponentVariableLinkIdDto updateVmComponentFullVariable = vmComponentVariableLinkService.updateVmComponentLinkVariable(id, vmComponentVariableLinkIdDto);
        VmComponentVariableLinkResponseDto response = vmComponentVariableLinkMapper.toControllerDto(updateVmComponentFullVariable);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Operation(
            summary = "Получить список связок переменных компонента vm-шаблона",
            description = "Возвращает список всех связок переменных компонента vm-шаблона",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список связок успешно получен",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VmComponentVariableLinkResponseDto.class))),
                    @ApiResponse(responseCode = "204", description = "Список связок пуст"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @GetMapping
    public ResponseEntity<List<VmComponentVariableLinkResponseDto>> getVmComponentVariableLinks() {
        List<VmComponentVariableLinkIdDto> vmComponentVariables = vmComponentVariableLinkService.getVmComponentLinkVariables();
        List<VmComponentVariableLinkResponseDto> response = vmComponentVariableLinkMapper.toControllerDtoList(vmComponentVariables);
        return vmComponentVariables.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Получить связки переменных компонентов, сгруппированные по названию компонента",
            description = "Возвращает связки переменных компонентов vm-шаблона в виде дерева, сгруппированные по названию компонента",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список связок успешно получен",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VmComponentVariableLinkResponseDto.class))),
                    @ApiResponse(responseCode = "204", description = "Список связок пуст"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @GetMapping("/grouped")
    public ResponseEntity<Map<String, List<VmComponentVariableLinkNodeResponseDto>>> getGroupedVariablesByComponentName() {
        Map<String, List<VmComponentVariableLinkNodeResponseDto>> groupedVariables = vmComponentVariableLinkService.getGroupedVariablesLinkByComponentName();

        return groupedVariables.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(groupedVariables);
    }

    @Operation(
            summary = "Удалить связку переменной компонента vm-шаблона",
            description = "Удаляет связку переменной компонента vm-шаблона по id",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Идентификатор переменной компонента vm-шаблона",
                            required = true,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Связка успешно удалена"),
                    @ApiResponse(responseCode = "404", description = "Связка не найдена"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVmComponentVariableLink(@PathVariable Integer id) {
        vmComponentVariableLinkService.deleteVmComponentVariableLink(id);
        return ResponseEntity.noContent().build();
    }
}
