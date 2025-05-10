package company.vm.service.generator.pdf;


import company.vm.dto.service.pdf.PdfComponentDto;
import company.vm.dto.service.pdf.PdfLinkedComponentDto;
import company.vm.enums.PdfBlockType;
import org.apache.logging.log4j.util.Strings;

import java.util.Objects;
import java.util.stream.Collectors;


public class CycleBlockProcessor implements ComponentProcessor {
    @Override
    public boolean canProcess(PdfComponentDto component) {
        return component.blockType() == PdfBlockType.CYCLE;
    }

    @Override
    public void process(PdfComponentDto component, TemplateBuilder builder, int indentLevel, String cycleVariablePrefix) {
        if (component.linkedComponents() == null || component.linkedComponents().isEmpty()) return;

        PdfLinkedComponentDto linkedComponent = component.linkedComponents().getFirst();
        String variable = linkedComponent.value();
        String id = String.format("$%s", linkedComponent.id());
        String item = "item" + linkedComponent.id();

        builder.addIf(indentLevel, id + " && " + id + " != ''")
                .addForeach(indentLevel, item, variable);

        builder.processComponents(component.childrenComponents(), indentLevel + 3, variable);

        component.childrenComponents().stream()
                .filter(child -> child.className() != null)
                .forEach(child -> builder.addLine(indentLevel + 3,
                        "{\"className\":\"%s\",\"label\":\"\",\"value\":\"%s\"}",
                        child.className(),
                        getComponentValue(child)
                ));

        builder.addEnd(indentLevel + 1)
                .addEnd(indentLevel);
    }

    private String getComponentValue(PdfComponentDto component) {
        if (component.linkedComponents() != null && !component.linkedComponents().isEmpty()) {
            String result = component.linkedComponents().stream()
                    .map(PdfLinkedComponentDto::value)
                    .filter(Objects::nonNull)
                    .filter(v -> !v.isEmpty())
                    .collect(Collectors.joining(" "));

            if (!result.isEmpty()) {
                return result;
            }
        }
        return component.value() != null ? component.value() : Strings.EMPTY;
    }

}
