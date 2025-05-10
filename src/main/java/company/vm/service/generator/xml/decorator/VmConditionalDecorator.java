package company.vm.service.generator.xml.decorator;


public class VmConditionalDecorator implements VmContentBuilder {
    private final String value;
    private final String condition;

    public VmConditionalDecorator(String value, String condition) {
        this.value = value;
        this.condition = condition;
    }

    @Override
    public StringBuilder build() {
        return new StringBuilder()
                .append("#if(").append(condition).append(")")
                .append(value)
                .append("#end");
    }

    public String complexConditionBuild() {
        String builder = "#if($" + condition + " && $" + condition + " != '')" +
                value +
                "#end";
        return builder;
    }
}
