package company.vm.controller;


import company.vm.dto.controller.VmTemplateSchemaVersionRequestDto;
import company.vm.dto.controller.VmTemplateSchemaVersionResponseDto;
import company.vm.dto.service.template.VmTemplateSchemaVersionDto;
import company.vm.dto.service.template.VmTemplateSchemaVersionIdDto;
import company.vm.mapper.VmTemplateSchemaVersionMapper;
import company.vm.service.VmTemplateSchemaVersionService;
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
@RequestMapping("/api/v1/vm-template-schema-versions")
@Tag(
        name = "Версии схем vm-шаблона",
        description = "API для создания версих схем vm-шаблона"
)
public class VmTemplateSchemaVersionController {

    private final VmTemplateSchemaVersionService vmTemplateSchemaVersionService;
    private final VmTemplateSchemaVersionMapper vmTemplateSchemaVersionMapper;

    public VmTemplateSchemaVersionController(VmTemplateSchemaVersionService vmTemplateSchemaVersionService,
                                             VmTemplateSchemaVersionMapper vmTemplateSchemaVersionMapper
    ) {
        this.vmTemplateSchemaVersionService = vmTemplateSchemaVersionService;
        this.vmTemplateSchemaVersionMapper = vmTemplateSchemaVersionMapper;
    }

    @Operation(
            summary = "Создать версию схемы vm-шаблона",
            description = "Создает новую версию схемы vm-шаблона и возвращает его данные",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = VmTemplateSchemaVersionRequestDto.class)
                    )

            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Версия схемы vm-шаблона успешно создана",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VmTemplateSchemaVersionResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные данные в запросе")
            }
    )
    @PostMapping
    public ResponseEntity<VmTemplateSchemaVersionResponseDto> createVmTemplateSchemaVersion(@RequestBody VmTemplateSchemaVersionRequestDto request) {
        VmTemplateSchemaVersionDto dto = vmTemplateSchemaVersionMapper.toServiceDto(request);
        VmTemplateSchemaVersionIdDto createdVmTemplateSchemaVersion = vmTemplateSchemaVersionService.createVmTemplateSchemaVersion(dto);
        VmTemplateSchemaVersionResponseDto response = vmTemplateSchemaVersionMapper.toControllerDto(createdVmTemplateSchemaVersion);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Получить версию схемы vm-шаблона по id",
            description = "Возвращает данные версии схемы vm-шаблона по его id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор версии схемы vm-шаблона",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Версия схемы vm-шаблона найдена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmTemplateSchemaVersionResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Версия схемы vm-шаблона не найдена")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<VmTemplateSchemaVersionResponseDto> getVmTemplateSchemaVersionById(@PathVariable Integer id) {
        VmTemplateSchemaVersionIdDto dto = vmTemplateSchemaVersionService.getVmTemplateSchemaVersionById(id);
        VmTemplateSchemaVersionResponseDto response = vmTemplateSchemaVersionMapper.toControllerDto(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Обновить версию схемы vm-шаблона по id",
            description = "Обновляет данные версию схемы vm-шаблона по id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор версию схемы vm-шаблона для обновления",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = VmTemplateSchemaVersionRequestDto.class)
                    )

            ),
            responses = {
                    @ApiResponse(responseCode = "202", description = "Версия схемы vm-шаблона обновлена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmTemplateSchemaVersionResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные данные в запросе"),
                    @ApiResponse(responseCode = "404", description = "Версия схемы vm-шаблона не найдена")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<VmTemplateSchemaVersionResponseDto> updateVmTemplateSchemaVersion(@PathVariable Integer id, @RequestBody VmTemplateSchemaVersionRequestDto request) {
        VmTemplateSchemaVersionDto dto = vmTemplateSchemaVersionMapper.toServiceDto(request);
        VmTemplateSchemaVersionIdDto updateVmTemplateSchemaVersion = vmTemplateSchemaVersionService.updateVmTemplateSchemaVersion(id, dto);
        VmTemplateSchemaVersionResponseDto response = vmTemplateSchemaVersionMapper.toControllerDto(updateVmTemplateSchemaVersion);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Operation(
            summary = "Получить список версий схемы vm-шаблона",
            description = "Возвращает список версий схемы vm-шаблона",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список версий схемы vm-шаблона возвращён",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VmTemplateSchemaVersionResponseDto.class))),
                    @ApiResponse(responseCode = "204", description = "Список версий схемы vm-шаблона пуст")
            }
    )
    @GetMapping
    public ResponseEntity<List<VmTemplateSchemaVersionResponseDto>> getVmTemplateSchemaVersionTypes() {
        List<VmTemplateSchemaVersionIdDto> vmTemplateSchemaVersions = vmTemplateSchemaVersionService.getVmTemplateSchemaVersions();
        List<VmTemplateSchemaVersionResponseDto> response = vmTemplateSchemaVersionMapper.toControllerDtoList(vmTemplateSchemaVersions);
        return response.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Удалить версию схемы vm-шаблона",
            description = "Удаляет версию схемы vm-шаблона по его id",
            parameters = @Parameter(
                    name = "id",
                    description = "Идентификатор версии схемы vm-шаблона для удаления",
                    required = true,
                    schema = @Schema(type = "integer", example = "1")
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Версия схемы vm-шаблона удалена"),
                    @ApiResponse(responseCode = "404", description = "Версия схемы vm-шаблона не найдена")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVmTemplateSchemaVersionType(@PathVariable Integer id) {
        vmTemplateSchemaVersionService.deleteVmTemplateSchemaVersion(id);
        return ResponseEntity.noContent().build();
    }
}
