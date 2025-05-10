package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Schema(description = "Запрос параметров на создание истории сборки vm-шаблона")
public record VmTemplateHistoryRequestDto(
        @Schema(description = "Название истории сборки шаблона", example = "pdf_98979453_Application.json")
        @NotBlank(message = "Название истории сборки шаблона не может быть пустым")
        String name,

        @Schema(description = "Описание истории сборки шаблона", example = "Истории сборки Pdf-шаблона услуги 98979453")
        String description,

        @Schema(description = "Идентификатор версии схемы", example = "1")
        @NotNull(message = "Id версии схемы обязателен")
        Integer versionId,

        @Schema(description = "Тип файла", example = "1")
        @NotNull(message = "Тип фалйа обязателен")
        Integer type
) {
}
