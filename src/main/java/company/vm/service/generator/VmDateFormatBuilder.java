package company.vm.service.generator;


public class VmDateFormatBuilder {
    private final static String BASE_DATE_FORMAT = "yyyy-MM-dd";

    public static String build(String variable, String dateFormat) {
        if (variable.equals("$dateTool")) {
            return String.format("$dateTool.get('%s')", dateFormat);
        }
        return String.format(
                "$dateTool.formatPattern('%s', $dateTool.toDate('%s', %s))",
                BASE_DATE_FORMAT, dateFormat, variable
        );
    }

}
