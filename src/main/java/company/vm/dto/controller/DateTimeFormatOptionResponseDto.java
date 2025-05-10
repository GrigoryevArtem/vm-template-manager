package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(
        description = "Dto для ответа передачи форматов даты и времени, включающее идентификатор, название и формат даты и времени."
)
public record DateTimeFormatOptionResponseDto(
        @Schema(
                description = "Идентификатор формата даты и времени",
                example = "1"
        )
        @NotNull
        Integer id,
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
