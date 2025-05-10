package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;


public record VmTemplateSchemaResponseDto(
        Integer id,
        @Schema(description = "Название схемы шаблона", example = "soc-petition-1.0.9.xsd")
        @NotBlank(message = "Название схемы шаблона не может быть пустым")
        String name,
        @Schema(description = "Версия схемы", example = "1.0.8")
        @NotNull(message = "Версия схемы обязательна")
        String version,
        @Schema(description = "Тип схемы", example = "base")
        @NotNull(message = "Тип схемы шаблона обязателен")
        String type,
        @Schema(description = "Дата и время загрузки файла", example = "2025-04-21T10:15:30Z")
        Instant uploadedAt,
        @Schema(description = "Дата и время обновления файла", example = "2025-04-21T12:45:00Z")
        Instant modifiedAt
) {
}