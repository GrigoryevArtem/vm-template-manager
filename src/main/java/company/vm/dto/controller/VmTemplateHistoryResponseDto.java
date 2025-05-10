package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;


@Schema(description = "Ответ параметров на создание истории сборки vm-шаблона")
public record VmTemplateHistoryResponseDto(
        @Schema(description = "Идентификатор истории сборки", example = "1")
        @NotNull
        Integer id,
        @Schema(description = "Название истории сборки шаблона", example = "pdf_98979453_Application.vm")
        @NotBlank(message = "Название истории сборки шаблона не может быть пустым")
        String name,
        @Schema(description = "Описание истории сборки шаблона", example = "Pdf-шаблон услуги 98979453")
        String description,
        @Schema(description = "Версия схемы", example = "1.0.8")
        @NotNull(message = "Версия схемы обязательна")
        String version,
        @Schema(description = "Тип файла", example = "pdf")
        @NotNull(message = "Тип фалйа обязателен")
        String type,
        @Schema(description = "Дата и время сборки истории файла", example = "2025-04-21T10:15:30Z")
        Instant uploadedAt,
        @Schema(description = "Дата и время обновления сбокрии истории файла", example = "2025-04-21T12:45:00Z")
        Instant modifiedAt
) {
}
