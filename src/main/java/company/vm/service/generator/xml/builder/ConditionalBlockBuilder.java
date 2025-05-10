package company.vm.service.generator.xml.builder;


import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.util.Strings;

import java.util.UUID;


public class ConditionalBlockBuilder {
    private final String id;
    private final String componentNamespace;
    private final String componentName;

    public ConditionalBlockBuilder(String id, String componentNamespace, String componentName) {
        this.id = id;
        this.componentNamespace = componentNamespace;
        this.componentName = componentName;
    }

    public String build(JsonNode answers) {
        if (answers == null || answers.isEmpty()) {
            return Strings.EMPTY;
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < answers.size(); i++) {
            JsonNode answer = answers.get(i);
            String value = safeText(answer.get("value"));
            String label = safeText(answer.get("label"));
            String guid = UUID.randomUUID().toString();

            appendCondition(builder, i, answers.size(), value);
            appendComponent(builder, guid, label);
        }

        builder.append("#end");
        return builder.toString();
    }

    private void appendCondition(StringBuilder builder, int index, int total, String value) {
        if (index == 0) {
            builder.append("#if ($").append(id).append(" == '").append(value).append("')");
        } else if (index == total - 1 && total == 2) {
            builder.append("#else");
        } else {
            builder.append("#elseif ($").append(id).append(" == '").append(value).append("')");
        }
    }

    private void appendComponent(StringBuilder builder, String guid, String label) {
        VmTagStructureBuilder tag = new VmTagStructureBuilder.Builder(componentNamespace, componentName)
                .withCode(guid)
                .withContent(label)
                .build();
        builder.append(tag.toString());
    }

    private String safeText(JsonNode node) {
        return node != null && !node.isNull() ? node.asText() : Strings.EMPTY;
    }
}


