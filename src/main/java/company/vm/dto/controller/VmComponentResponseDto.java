package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(
        description = "Dto для ответа по представлению компонента vm-шаблона, включающее название и описание компонента."
)
public record VmComponentResponseDto(
        @Schema(
                description = "Идентификатор компонента vm-шаблона.",
                example = "1"
        )
        @NotNull
        Integer id,
        @Schema(
                description = "Название компонента vm-шаблона.",
                example = "Компонент A"
        )
        @NotNull
        String name,

        @Schema(
                description = "Описание компонента vm-шаблона.",
                example = "Это описание компонента vm-шаблона."
        )
        String description
) {
}
