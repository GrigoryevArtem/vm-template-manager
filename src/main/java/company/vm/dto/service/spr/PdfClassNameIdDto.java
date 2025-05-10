package company.vm.dto.service.spr;


public record PdfClassNameIdDto(
        Integer id,
        String name,
        String description,
        Boolean hasValue
) {
}
