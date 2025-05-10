package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Schema(description = "Запрос параметров на создание vm-шаблона")
public record VmTemplateRequestDto(
        @Schema(description = "Название шаблона", example = "pdf_98979453_Application.vm")
        @NotBlank(message = "Название шаблона не может быть пустым")
        String name,

        @Schema(description = "Описание шаблона", example = "Pdf-шаблон услуги 98979453")
        String description,

        @Schema(description = "Идентификатор версии схемы", example = "1")
        @NotNull(message = "Id версии схемы обязателен")
        Integer versionId,

        @Schema(description = "Тип файла", example = "1")
        @NotNull(message = "Тип фалйа обязателен")
        Integer type
) {
}
