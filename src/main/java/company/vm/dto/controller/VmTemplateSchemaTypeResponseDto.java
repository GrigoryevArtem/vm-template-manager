package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(
        description = "Dto для ответа предоставления типа схемы (основная/вложенная)."
)
public record VmTemplateSchemaTypeResponseDto(
        @Schema(
                description = "Идентификатор типа схемы",
                example = "1"
        )
        @NotNull
        Integer id,

        @Schema(
                description = "Код типа схемы",
                example = "base"
        )
        @NotNull
        String name,

        @Schema(
                description = "Описание типа схемы.",
                example = "Основная"
        )
        @NotNull
        String description
) {
}
