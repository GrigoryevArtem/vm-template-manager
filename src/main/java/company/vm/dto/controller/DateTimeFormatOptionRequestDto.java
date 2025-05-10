package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(
        description = "Dto для передачи форматов даты и времени, включающее название и формат даты и времени."
)
public record DateTimeFormatOptionRequestDto(
        @Schema(
                description = "Название формата даты и времени",
                example = "Европейский"
        )
        String name,
        @Schema(
                description = "Паттерн формата даты и времени",
                example = "dd.MM.yyyy"
        )
        @NotNull
        String formatPattern
) {
}
