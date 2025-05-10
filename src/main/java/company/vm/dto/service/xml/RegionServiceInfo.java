package company.vm.dto.service.xml;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record RegionServiceInfo(
        @NotBlank(message = "Ссылка uriBase не может быть пустой")
        String uriBase,
        @NotBlank(message = "Ссылка uriType не может быть пустой")
        String uriType,
        @Pattern(regexp = "\\d{8}|\\d{11}", message = "Код ОКТМО должен состоять из 8 или 11 цифр")
        String oktmo,
        String serviceName,
        String serviceCode
) {
}
