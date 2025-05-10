package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;


@Schema(description = "Ответ параметров на создание vm-шаблона")
public record VmTemplateResponseDto(
        Integer id,
        @Schema(description = "Название шаблона", example = "pdf_98979453_Application.vm")
        @NotBlank(message = "Название шаблона не может быть пустым")
        String name,
        @Schema(description = "Описание шаблона", example = "Pdf-шаблон услуги 98979453")
        String description,
        @Schema(description = "Версия схемы", example = "1.0.8")
        @NotNull(message = "Версия схемы обязательна")
        String version,
        @Schema(description = "Тип файла", example = "pdf")
        @NotNull(message = "Тип фалйа обязателен")
        String type,
        @Schema(description = "Дата и время загрузки файла", example = "2025-04-21T10:15:30Z")
        Instant uploadedAt,
        @Schema(description = "Дата и время обновления файла", example = "2025-04-21T12:45:00Z")
        Instant modifiedAt
) {
}
