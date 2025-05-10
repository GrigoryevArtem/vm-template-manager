package company.vm.controller;


import company.vm.dto.controller.VmTemplateTypeRequestDto;
import company.vm.dto.controller.VmTemplateTypeResponseDto;
import company.vm.dto.service.template.VmTemplateTypeDto;
import company.vm.dto.service.template.VmTemplateTypeIdDto;
import company.vm.mapper.VmTemplateTypeMapper;
import company.vm.service.VmTemplateTypeService;
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
@RequestMapping("/api/v1/vm-template-types")
@Tag(
        name = "Типы vm-шаблона",
        description = "API для управления типами vm-шаблона"
)
public class VmTemplateTypeController {
    private final VmTemplateTypeService vmTemplateTypeService;
    private final VmTemplateTypeMapper vmTemplateTypeMapper;

    public VmTemplateTypeController(VmTemplateTypeService vmTemplateTypeService,
                                    VmTemplateTypeMapper vmTemplateTypeMapper
    ) {
        this.vmTemplateTypeService = vmTemplateTypeService;
        this.vmTemplateTypeMapper = vmTemplateTypeMapper;
    }

    @Operation(
            summary = "Создать тип vm-шаблона",
            description = "Создает новый тип vm-шаблона и возвращает его данные",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = VmTemplateTypeRequestDto.class)
                    )

            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Тип vm-шаблона успешно создан",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VmTemplateTypeResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные данные в запросе")
            }
    )
    @PostMapping
    public ResponseEntity<VmTemplateTypeResponseDto> createVmTemplateType(@RequestBody VmTemplateTypeRequestDto request) {
        VmTemplateTypeDto dto = vmTemplateTypeMapper.toServiceDto(request);
        VmTemplateTypeIdDto createdVmTemplate = vmTemplateTypeService.createVmTemplateType(dto);
        VmTemplateTypeResponseDto response = vmTemplateTypeMapper.toControllerDto(createdVmTemplate);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Получить тип vm-шаблона по id",
            description = "Возвращает данные типа vm-шаблона по его id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор типа vm-шаблона",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Тип vm-шаблона найдена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmTemplateTypeResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Тип vm-шаблона не найдена")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<VmTemplateTypeResponseDto> getVmTemplateTypeById(@PathVariable Integer id) {
        VmTemplateTypeIdDto dto = vmTemplateTypeService.getVmTemplateTypeById(id);
        VmTemplateTypeResponseDto response = vmTemplateTypeMapper.toControllerDto(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Обновить тип vm-шаблона по id",
            description = "Обновляет данные типа vm-шаблона по id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор типа vm-шаблона для обновления",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = VmTemplateTypeRequestDto.class)
                    )

            ),
            responses = {
                    @ApiResponse(responseCode = "202", description = "Тип vm-шаблона обновлен",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmTemplateTypeResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные данные в запросе"),
                    @ApiResponse(responseCode = "404", description = "Тип vm-шаблона не найден")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<VmTemplateTypeResponseDto> updateVmTemplateType(@PathVariable Integer id, @RequestBody VmTemplateTypeRequestDto request) {
        VmTemplateTypeDto dto = vmTemplateTypeMapper.toServiceDto(request);
        VmTemplateTypeIdDto updatedVmTemplateType = vmTemplateTypeService.updateVmTemplateType(id, dto);
        VmTemplateTypeResponseDto response = vmTemplateTypeMapper.toControllerDto(updatedVmTemplateType);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Operation(
            summary = "Получить список типов vm-шаблона",
            description = "Возвращает список типов vm-шаблона",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список типов vm-шаблона возвращён",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmTemplateTypeResponseDto.class))),
                    @ApiResponse(responseCode = "204", description = "Список типов vm-шаблона пуст")
            }
    )
    @GetMapping
    public ResponseEntity<List<VmTemplateTypeResponseDto>> getVmTemplateTypes() {
        List<VmTemplateTypeIdDto> vmTemplateTypes = vmTemplateTypeService.getVmTemplateTypes();
        List<VmTemplateTypeResponseDto> response = vmTemplateTypeMapper.toControllerDtoList(vmTemplateTypes);
        return response.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Удалить тип vm-шаблона",
            description = "Удаляет тип vm-шаблона по его id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор типа vm-шаблона для удаления",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Тип vm-шаблона удален"),
                    @ApiResponse(responseCode = "404", description = "Тип vm-шаблона не найден")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVmTemplateType(@PathVariable Integer id) {
        vmTemplateTypeService.deleteVmTemplateType(id);
        return ResponseEntity.noContent().build();
    }
}
