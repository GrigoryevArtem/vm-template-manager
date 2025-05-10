package company.vm.service.generator.xml.builder;


public class JsonLogicCondition {
    private final String variable;
    private final String operator;
    private final String value;

    public JsonLogicCondition(String variable, String operator, String value) {
        this.variable = variable;
        this.operator = operator;
        this.value = value;
    }

    public String toVelocity() {
        return String.format("$%s %s '%s'", variable, operator, value);
    }
}

