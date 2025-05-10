package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(
        description = "Dto для запроса представления переменной компонента vm-шаблона, включающее название и описание переменной."
)
public record VmComponentVariableRequestDto(

        @Schema(
                description = "Название переменной компонента vm-шаблона.",
                example = "VariableName"
        )
        @NotNull
        String name,

        @Schema(
                description = "Описание переменной компонента vm-шаблона.",
                example = "Описание переменной для компонента vm-шаблона."
        )
        String description

) {
}
