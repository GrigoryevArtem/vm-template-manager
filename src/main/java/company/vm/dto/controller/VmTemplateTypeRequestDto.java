package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


public record VmTemplateTypeRequestDto(
        @Schema(
                description = "Название типа шаблона",
                example = "pdf"
        )
        @NotNull
        String name,
        @Schema(
                description = "Описание типа шаблона",
                example = "Pdf-шаблон"
        )
        String description
) {
}
