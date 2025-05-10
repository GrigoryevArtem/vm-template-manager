package company.vm.service.generator;


public class VmFileNameGenerator {
    private static final String FORMAT = ".vm";
    private static final String PDF_TEMPLATE = "pdf_%s_Applicant";
    private static final String XML_TEMPLATE = "%s_Applicant";

    public static String generatePdfTemplateFileName(String serviceId) {
        return String.format(PDF_TEMPLATE, serviceId) + FORMAT;
    }

    public static String generateXmlTemplateFileName(String serviceId) {
        return String.format(XML_TEMPLATE, serviceId) + FORMAT;
    }

    public static String getFormat() {
        return FORMAT;
    }
}
