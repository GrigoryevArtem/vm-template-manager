package company.vm.dto.service.template;


import java.time.Instant;


public record VmTemplateDto(
        Integer id,
        String name,
        String description,
        String version,
        String type,
        Instant uploadedAt,
        Instant modifiedAt
) {
}
