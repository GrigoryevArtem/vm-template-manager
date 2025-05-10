package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;


@Schema(
        description = "Dto для ответа представления связки переменной компонента vm-шаблона. Содержит дерево зависимостей атрибутов"
)
public record VmComponentVariableLinkNodeResponseDto(
        @Schema(
                description = "Имя переменной.",
                example = "additionalTownType"
        )
        String name,
        @Schema(
                description = "Описание переменной.",
                example = "Тип дополнительного населённого пункта"
        )
        String description,
        @Schema(
                description = "Список дочерних элементов.",
                example = "[]"
        )
        List<VmComponentVariableLinkNodeResponseDto> children
) {
}
