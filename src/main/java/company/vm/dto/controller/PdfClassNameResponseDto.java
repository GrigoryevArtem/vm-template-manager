package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(
        description = "Dto для ответа представления инструкции pdf, включающее идентификатор, название и описание инструкции."
)
public record PdfClassNameResponseDto(
        @Schema(
                description = "Идентификатор инструкции PDF.",
                example = "1"
        )
        @NotNull
        Integer id,
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
