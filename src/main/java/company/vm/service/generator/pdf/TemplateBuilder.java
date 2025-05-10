package company.vm.service.generator.pdf;


import company.vm.dto.service.pdf.PdfComponentDto;

import java.util.List;


public class TemplateBuilder {
    private final StringBuilder template = new StringBuilder("[\n");
    private final List<ComponentProcessor> processors;

    public TemplateBuilder() {
        this.processors = List.of(
                new ConditionBlockProcessor(),
                new CycleBlockProcessor(),
                new RegularComponentProcessor()
        );
    }

    public TemplateBuilder addLine(int indentLevel, String format, Object... args) {
        String line = String.format(format, args);
        template.append("    ".repeat(indentLevel)).append(line).append(",\n");
        return this;
    }

    public TemplateBuilder addIf(int indentLevel, String condition) {
        template.append("    ".repeat(indentLevel)).append("#if (").append(condition).append(")\n");
        return this;
    }

    public TemplateBuilder addElseIf(int indentLevel, String condition) {
        template.append("    ".repeat(indentLevel)).append("#elseif (").append(condition).append(")\n");
        return this;
    }

    public TemplateBuilder addElse(int indentLevel) {
        template.append("    ".repeat(indentLevel)).append("#else\n");
        return this;
    }

    public TemplateBuilder addEnd(int indentLevel) {
        template.append("    ".repeat(indentLevel)).append("#end\n");
        return this;
    }

    public TemplateBuilder addForeach(int indentLevel, String item, String collection) {
        template.append("    ".repeat(indentLevel))
                .append("#foreach($").append(item).append(" in ").append(collection).append(")\n");
        return this;
    }

    public TemplateBuilder processComponents(List<PdfComponentDto> components, int indentLevel, String cycleVariablePrefix) {
        if (components == null) return this;

        components
                .forEach(component -> processors.stream()
                        .filter(p -> p.canProcess(component))
                        .findFirst()
                        .ifPresent(p -> p.process(component, this, indentLevel, cycleVariablePrefix)));

        return this;
    }

    public String build() {
        return template
                .deleteCharAt(template.lastIndexOf(","))
                .append("]")
                .toString();
    }
}
