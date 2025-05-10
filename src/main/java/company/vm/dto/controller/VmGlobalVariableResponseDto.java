package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


public record VmGlobalVariableResponseDto(
        @Schema(
                description = "Идентификатор глобальной переменной.",
                example = "1"
        )
        @NotNull
        Integer id,

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
