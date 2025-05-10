package company.vm.dto.service.template;


import java.time.Instant;


public record VmTemplateSchemaDto(
        Integer id,
        String name,
        String version,
        String type,
        Instant uploadedAt,
        Instant modifiedAt
) {
}
