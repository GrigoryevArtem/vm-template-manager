package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(
        description = "Dto для ответа предоставления версии схемы."
)
public record VmTemplateSchemaVersionResponseDto(
        @Schema(
                description = "Идентификатор версии схемы",
                example = "1"
        )
        @NotNull
        Integer id,
        @Schema(
                description = "Название версии схемы",
                example = "1.0.6"
        )
        @NotNull
        String name
) {
}
