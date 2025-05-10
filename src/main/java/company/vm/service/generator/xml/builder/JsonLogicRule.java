package company.vm.service.generator.xml.builder;


import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;


public class JsonLogicRule {
    private final List<JsonLogicCondition> conditions;
    @Getter
    private final String text;

    public JsonLogicRule(List<JsonLogicCondition> conditions, String text) {
        this.conditions = conditions;
        this.text = text;
    }

    public String getCombinedConditions() {
        return conditions.stream()
                .map(JsonLogicCondition::toVelocity)
                .collect(Collectors.joining(" && "));
    }

    public boolean hasConditions() {
        return conditions != null && !conditions.isEmpty();
    }

}
