package company.vm.controller;


import company.vm.dto.controller.VmComponentRequestDto;
import company.vm.dto.controller.VmComponentResponseDto;
import company.vm.dto.service.spr.VmComponentDto;
import company.vm.dto.service.spr.VmComponentIdDto;
import company.vm.mapper.VmComponentMapper;
import company.vm.service.VmComponentService;
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
@RequestMapping("/api/v1/vm-components")
@Tag(
        name = "Компоненты vm-шаблона",
        description = "API для управления компонентами vm-шаблона"
)
public class VmComponentController {

    private final VmComponentService vmComponentService;
    private final VmComponentMapper vmComponentMapper;

    public VmComponentController(VmComponentService vmComponentService,
                                 VmComponentMapper vmComponentMapper
    ) {
        this.vmComponentService = vmComponentService;
        this.vmComponentMapper = vmComponentMapper;
    }

    @Operation(
            summary = "Создать компонент vm-шаблона",
            description = "Создает новый компонент vm-шаблона и возвращает его данные",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = VmComponentRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Новый компонент успешно создан",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VmComponentRequestDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            }
    )
    @PostMapping
    public ResponseEntity<VmComponentResponseDto> createVmComponent(@RequestBody VmComponentRequestDto request) {
        VmComponentDto vmComponentDto = vmComponentMapper.toServiceDto(request);
        VmComponentIdDto createdVmComponent = vmComponentService.createVmComponent(vmComponentDto);
        VmComponentResponseDto response = vmComponentMapper.toControllerDto(createdVmComponent);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Получить компонент vm-шаблона по id",
            description = "Возвращает данные компонента vm-шаблона по его id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор компонента vm-шаблона",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Компонент найден"),
                    @ApiResponse(responseCode = "404", description = "Компонент не найден")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<VmComponentResponseDto> getVmComponentById(@PathVariable Integer id) {
        VmComponentIdDto vmComponentDto = vmComponentService.getVmComponentById(id);
        VmComponentResponseDto response = vmComponentMapper.toControllerDto(vmComponentDto);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Обновить компонент vm-шаблона по id",
            description = "Обновляет данные компонента vm-шаблона по его id",
            parameters = @Parameter(
                    name = "id",
                    description = "Уникальный идентификатор компонента vm-шаблона",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = VmComponentRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Компонент vm-шаблона успешно обновлен",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VmComponentRequestDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Компонент vm-шаблона не найден"),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<VmComponentResponseDto> updateVmComponent(@PathVariable Integer id, @RequestBody VmComponentRequestDto request) {
        VmComponentDto vmComponentDto = vmComponentMapper.toServiceDto(request);
        VmComponentIdDto updatedVmComponent = vmComponentService.updateVmComponent(id, vmComponentDto);
        VmComponentResponseDto response = vmComponentMapper.toControllerDto(updatedVmComponent);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Operation(
            summary = "Получить список компонентов vm-шаблона",
            description = "Возвращает список всех компонентов vm-шаблона",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список компонентов возвращен"),
                    @ApiResponse(responseCode = "204", description = "Список компонентов пуст")
            }
    )
    @GetMapping
    public ResponseEntity<List<VmComponentResponseDto>> getVmComponents() {
        List<VmComponentIdDto> vmComponents = vmComponentService.getVmComponents();
        List<VmComponentResponseDto> response = vmComponentMapper.toControllerDtoList(vmComponents);
        return response.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Удалить компонент vm-шаблона",
            description = "Удаляет компонент vm-шаблона по его id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор компонента vm-шаблона",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Компонент успешно удален"),
                    @ApiResponse(responseCode = "404", description = "Компонент не найден")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVmComponent(@PathVariable Integer id) {
        vmComponentService.deleteVmComponent(id);
        return ResponseEntity.noContent().build();
    }
}
