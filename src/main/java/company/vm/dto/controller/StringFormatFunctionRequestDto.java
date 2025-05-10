package company.vm.dto.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


@Schema(
        description = "Dto для запроса представления фунций для форматирования строк, включающее идентификатор, название и описание инструкции."
)
public record StringFormatFunctionRequestDto(
        @Schema(
                description = "Название тэга xml.",
                example = "Room"
        )
        @NotBlank(message = "Название тэга не может быть пустым")
        String tagName,
        @Schema(
                description = "Функиця, применяемая для форматирования.",
                example = ".replaceAll(\"[(]\", \"'...'\").replaceAll(\"[)]\",\"'....'\").replaceAll(\"/\", \"''\")"
        )
        @NotBlank(message = "Функция не может быть пустой")
        String function
) {
}
