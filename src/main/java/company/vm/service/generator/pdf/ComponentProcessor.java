package company.vm.service.generator.pdf;


import company.vm.dto.service.pdf.PdfComponentDto;


public interface ComponentProcessor {
    boolean canProcess(PdfComponentDto component);

    void process(PdfComponentDto component, TemplateBuilder builder, int indentLevel, String cycleVariablePrefix);
}
