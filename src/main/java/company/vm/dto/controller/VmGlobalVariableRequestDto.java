package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(
        description = "Dto для представления глобальной переменной, включающее имя и описание глобальной переменной."
)
public record VmGlobalVariableRequestDto(

        @Schema(
                description = "Имя глобальной переменной.",
                example = "server_name"
        )
        @NotNull
        String name,

        @Schema(
                description = "Описание глобальной переменной.",
                example = "Имя сервера для шаблона."
        )
        String description

) {
}
