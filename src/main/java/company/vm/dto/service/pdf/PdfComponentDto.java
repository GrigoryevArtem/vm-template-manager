package company.vm.dto.service.pdf;


import company.vm.enums.ConditionType;
import company.vm.enums.PdfBlockType;

import java.util.List;


public record PdfComponentDto(
        String id,
        String className,
        String label,
        String value,
        List<PdfLinkedComponentDto> linkedComponents,
        List<PdfComponentDto> childrenComponents,
        Boolean hasValue,
        ConditionType condition,
        PdfBlockType blockType
) {
}
