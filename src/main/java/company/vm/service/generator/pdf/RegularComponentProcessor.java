package company.vm.service.generator.pdf;


import company.vm.dto.service.pdf.PdfComponentDto;
import company.vm.enums.PdfBlockType;
import company.vm.service.generator.VmDateFormatBuilder;
import org.apache.logging.log4j.util.Strings;

import java.util.Objects;
import java.util.stream.Collectors;


public class RegularComponentProcessor implements ComponentProcessor {
    @Override
    public boolean canProcess(PdfComponentDto component) {
        return component.blockType() == PdfBlockType.SIMPLE;
    }

    @Override
    public void process(PdfComponentDto component, TemplateBuilder builder, int indentLevel, String cycleVariablePrefix) {
        if (component.className() == null && component.label() == null && component.value() == null) {
            return;
        }

        builder.addLine(indentLevel,
                "{\"className\":\"%s\",\"label\":\"%s\",\"value\":\"%s\"}",
                component.className() != null ? component.className() : "",
                component.label() != null ? component.label() : "",
                getComponentValue(component, cycleVariablePrefix)
        );

        if (component.childrenComponents() != null && !component.childrenComponents().isEmpty()) {
            builder.processComponents(component.childrenComponents(), indentLevel + 1, null);
        }
    }

    private String getComponentValue(PdfComponentDto component, String cycleVariablePrefix) {
        if (component.linkedComponents() != null && !component.linkedComponents().isEmpty()) {
            String result = component.linkedComponents().stream()
                    .map(link -> {
                        if (link.dateFormat() != null) {
                            return VmDateFormatBuilder.build(link.value(), link.dateFormat());
                        } else {
                            if (cycleVariablePrefix != null && link.value().contains(cycleVariablePrefix)) {
                                String item = "$item" + link.id();
                                return link.value().replace(cycleVariablePrefix, item);
                            }
                            return link.value();
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(val -> !val.isEmpty())
                    .collect(Collectors.joining(" "));

            if (!result.isEmpty()) {
                return result;
            }
        }

        return component.value() != null ? component.value() : Strings.EMPTY;
    }

}
