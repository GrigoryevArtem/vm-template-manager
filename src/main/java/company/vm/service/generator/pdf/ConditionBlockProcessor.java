package company.vm.service.generator.pdf;


import company.vm.dto.service.pdf.PdfComponentDto;
import company.vm.dto.service.pdf.PdfLinkedComponentDto;
import company.vm.enums.ConditionType;
import company.vm.enums.PdfBlockType;
import org.apache.logging.log4j.util.Strings;

import java.util.List;
import java.util.stream.Collectors;


public class ConditionBlockProcessor implements ComponentProcessor {
    @Override
    public boolean canProcess(PdfComponentDto component) {
        return component.blockType() == PdfBlockType.CONDITION;
    }

    @Override
    public void process(PdfComponentDto component, TemplateBuilder builder, int indentLevel, String cycleVariablePrefix) {
        List<PdfComponentDto> children = component
                .childrenComponents()
                .stream()
                .toList();

        boolean hasConditions = false;

        for (PdfComponentDto child : children) {
            if (child.condition() == null) continue;

            hasConditions = true;
            String condition = buildCondition(child.linkedComponents());

            switch (child.condition()) {
                case ConditionType.IF -> builder.addIf(indentLevel, condition);
                case ConditionType.ELSEIF -> builder.addElseIf(indentLevel, condition);
                case ConditionType.ELSE -> builder.addElse(indentLevel);
            }

            builder.processComponents(child.childrenComponents(), indentLevel + 1, cycleVariablePrefix);
        }

        if (hasConditions) {
            builder.addEnd(indentLevel);
        }
    }

    private String buildCondition(List<PdfLinkedComponentDto> linkedComponents) {
        if (linkedComponents == null || linkedComponents.isEmpty()) return Strings.EMPTY;

        return linkedComponents.stream()
                .filter(lc -> lc.value() != null && (lc.answer() != null || lc.answerText() != null))
                .map(lc -> String.format("%s %s '%s'",
                        lc.value(),
                        lc.operator() != null ? lc.operator() : Strings.EMPTY,
                        lc.answer() != null ? lc.answer() : lc.answerText()))
                .collect(Collectors.joining(" && "));
    }
}