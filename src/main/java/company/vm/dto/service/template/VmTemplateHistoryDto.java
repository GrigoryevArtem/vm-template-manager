package company.vm.dto.service.template;


import java.time.Instant;


public record VmTemplateHistoryDto(
        Integer id,
        String name,
        String description,
        String version,
        String type,
        Instant uploadedAt,
        Instant modifiedAt
) {
}
