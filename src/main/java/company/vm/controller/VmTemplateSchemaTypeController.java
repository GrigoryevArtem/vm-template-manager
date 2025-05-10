package company.vm.controller;


import company.vm.dto.controller.VmTemplateSchemaTypeRequestDto;
import company.vm.dto.controller.VmTemplateSchemaTypeResponseDto;
import company.vm.dto.service.template.VmTemplateSchemaTypeDto;
import company.vm.dto.service.template.VmTemplateSchemaTypeIdDto;
import company.vm.mapper.VmTemplateSchemaTypeMapper;
import company.vm.service.VmTemplateSchemaTypeService;
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
@RequestMapping("/api/v1/vm-template-schema-types")
@Tag(
        name = "Типы схем vm-шаблона",
        description = "API для создания типов схем vm-шаблона"
)
public class VmTemplateSchemaTypeController {

    private final VmTemplateSchemaTypeService vmTemplateSchemaTypeService;
    private final VmTemplateSchemaTypeMapper vmTemplateSchemaTypeMapper;

    public VmTemplateSchemaTypeController(VmTemplateSchemaTypeService vmTemplateSchemaTypeService,
                                          VmTemplateSchemaTypeMapper vmTemplateSchemaTypeMapper) {
        this.vmTemplateSchemaTypeService = vmTemplateSchemaTypeService;
        this.vmTemplateSchemaTypeMapper = vmTemplateSchemaTypeMapper;
    }

    @Operation(
            summary = "Создать тип схемы vm-шаблона",
            description = "Создает новый тип схемы vm-шаблона и возвращает его данные",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = VmTemplateSchemaTypeRequestDto.class)
                    )

            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Тип схемы vm-шаблона успешно создан",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VmTemplateSchemaTypeResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные данные в запросе")
            }
    )
    @PostMapping
    public ResponseEntity<VmTemplateSchemaTypeResponseDto> createVmTemplateSchemaType(@RequestBody VmTemplateSchemaTypeRequestDto request) {
        VmTemplateSchemaTypeDto dto = vmTemplateSchemaTypeMapper.toServiceDto(request);
        VmTemplateSchemaTypeIdDto createdVmTemplateSchemaType = vmTemplateSchemaTypeService.createVmTemplateSchemaType(dto);
        VmTemplateSchemaTypeResponseDto response = vmTemplateSchemaTypeMapper.toControllerDto(createdVmTemplateSchemaType);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Получить тип схемы vm-шаблона по id",
            description = "Возвращает тип схемы vm-шаблона по его id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор типа схемы vm-шаблона",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Тип схемы vm-шаблона найдена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmTemplateSchemaTypeResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Тип схемы vm-шаблона не найдена")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<VmTemplateSchemaTypeResponseDto> getVmTemplateSchemaTypeById(@PathVariable Integer id) {
        VmTemplateSchemaTypeIdDto dto = vmTemplateSchemaTypeService.getVmTemplateSchemaTypeById(id);
        VmTemplateSchemaTypeResponseDto response = vmTemplateSchemaTypeMapper.toControllerDto(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Обновить тип схемы vm-шаблона по id",
            description = "Обновляет данные типа схемы vm-шаблона по id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор типа схемы vm-шаблона для обновления",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = VmTemplateSchemaTypeRequestDto.class)
                    )

            ),
            responses = {
                    @ApiResponse(responseCode = "202", description = "Тип схемы vm-шаблона обновлен",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmTemplateSchemaTypeResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные данные в запросе"),
                    @ApiResponse(responseCode = "404", description = "Тип схемы vm-шаблона не найден")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<VmTemplateSchemaTypeResponseDto> updateVmTemplateSchemaType(@PathVariable Integer id, @RequestBody VmTemplateSchemaTypeRequestDto request) {
        VmTemplateSchemaTypeDto dto = vmTemplateSchemaTypeMapper.toServiceDto(request);
        VmTemplateSchemaTypeIdDto updatedVmTemplateSchemaType = vmTemplateSchemaTypeService.updateVmTemplateSchemaType(id, dto);
        VmTemplateSchemaTypeResponseDto response = vmTemplateSchemaTypeMapper.toControllerDto(updatedVmTemplateSchemaType);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Operation(
            summary = "Получить список типов схемы vm-шаблона",
            description = "Возвращает список типов схемы vm-шаблона",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список типов схемы vm-шаблона возвращён",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmTemplateSchemaTypeResponseDto.class))),
                    @ApiResponse(responseCode = "204", description = "Список типов схемы vm-шаблона пуст")
            }
    )
    @GetMapping
    public ResponseEntity<List<VmTemplateSchemaTypeResponseDto>> getVmTemplateSchemaTypes() {
        List<VmTemplateSchemaTypeIdDto> vmTemplateSchemaTypes = vmTemplateSchemaTypeService.getVmTemplateSchemaTypes();
        List<VmTemplateSchemaTypeResponseDto> response = vmTemplateSchemaTypeMapper.toControllerDtoList(vmTemplateSchemaTypes);
        return response.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Удалить тип схемы vm-шаблона",
            description = "Удаляет тип схемы vm-шаблона по его id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор типа схемы vm-шаблона для удаления",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Тип схемы vm-шаблона удален"),
                    @ApiResponse(responseCode = "404", description = "Тип схемы vm-шаблона не найден")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVmTemplateSchemaType(@PathVariable Integer id) {
        vmTemplateSchemaTypeService.deleteVmTemplateSchemaType(id);
        return ResponseEntity.noContent().build();
    }
}
