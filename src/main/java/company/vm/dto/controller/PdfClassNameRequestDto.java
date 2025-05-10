package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(
        description = "Dto для запроса представления инструкции pdf, включающее название и описание инструкции."
)
public record PdfClassNameRequestDto(
        @Schema(
                description = "Название инструкции PDF.",
                example = "Инструкция по использованию системы"
        )
        @NotNull
        String name,

        @Schema(
                description = "Описание инструкции PDF.",
                example = "Инструкция, описывающая основные шаги для использования системы."
        )
        String description,

        @Schema(
                description = "Флаг возможности заполнять текста у блока.",
                example = "true"
        )
        Boolean hasValue
) {

}
