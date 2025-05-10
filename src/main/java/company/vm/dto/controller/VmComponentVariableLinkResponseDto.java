package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(
        description = "Dto для ответа представления связки переменной компонента vm-шаблона. Содержит информацию о переменной, компоненте и их родительской связи."
)
public record VmComponentVariableLinkResponseDto(
        @Schema(
                description = "Идентификатор связки переменной компонента vm-шаблона.",
                example = "1"
        )
        @NotNull
        Integer id,

        @Schema(
                description = "Идентификатор переменной компонента vm-шаблона.",
                example = "101"
        )
        @NotNull
        Integer variableId,

        @Schema(
                description = "Идентификатор компонента vm-шаблона, с которым связана переменная.",
                example = "2001"
        )
        @NotNull
        Integer componentId,

        @Schema(
                description = "Идентификатор родительской связки для создания иерархической структуры переменных.",
                example = "5001"
        )
        Integer parentId
) {
}
