package company.vm.service.generator.xml.builder;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class JsonLogicBlockBuilder {

    public String convertToVelocity(JsonNode node, VmTagStructureBuilder tagStructureBuilder) {
        String header = node.get("header").asText();
        ArrayNode linkedValues = (ArrayNode) node.get("linkedValues");

        Map<String, List<JsonLogicRule>> argumentRules = new LinkedHashMap<>();

        for (JsonNode linkedValue : linkedValues) {
            String argument = linkedValue.get("argument").asText();
            List<JsonLogicRule> rules = parseLogic(linkedValue.get("jsonLogic"));
            argumentRules.put(argument, rules);
        }

        List<List<JsonLogicRule>> ruleGroups = new ArrayList<>(argumentRules.values());
        List<List<JsonLogicRule>> combinations = cartesianProduct(ruleGroups);

        // Сортируем так, чтобы сначала шли с условиями, а последним — без условий
        combinations.sort((a, b) -> {
            boolean aHas = a.stream().allMatch(JsonLogicRule::hasConditions);
            boolean bHas = b.stream().allMatch(JsonLogicRule::hasConditions);
            return Boolean.compare(!aHas, !bHas);
        });

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < combinations.size(); i++) {
            List<JsonLogicRule> combo = combinations.get(i);
            List<String> conditions = combo.stream()
                    .filter(JsonLogicRule::hasConditions)
                    .map(JsonLogicRule::getCombinedConditions)
                    .collect(Collectors.toList());

            String filledHeader = header;
            int argIndex = 0;
            for (String arg : argumentRules.keySet()) {
                if (argIndex < combo.size()) {
                    filledHeader = filledHeader.replace("${" + arg + "}", combo.get(argIndex).getText());
                    argIndex++;
                }
            }

            if (!conditions.isEmpty()) {
                String condition = String.join(" && ", conditions);
                if (i == 0) {
                    sb.append("#if (").append(condition).append(")\n");
                } else {
                    sb.append("#elseif(").append(condition).append(")\n");
                }
            } else {
                sb.append("#else\n");
            }

            tagStructureBuilder.setContent(filledHeader);
            sb.append(tagStructureBuilder);
        }

        sb.append("#end\n");
        return sb.toString();
    }

    private List<JsonLogicRule> parseLogic(JsonNode logicNode) {
        List<JsonLogicRule> rules = new ArrayList<>();
        ArrayNode ifArray = (ArrayNode) logicNode.get("if");

        for (int i = 0; i < ifArray.size() - 1; i += 2) {
            JsonNode conditionNode = ifArray.get(i);
            String resultText = ifArray.get(i + 1).asText();
            List<JsonLogicCondition> conditions = parseConditions(conditionNode);
            rules.add(new JsonLogicRule(conditions, resultText));
        }

        if (ifArray.size() % 2 == 1) {
            rules.add(new JsonLogicRule(List.of(), ifArray.get(ifArray.size() - 1).asText()));
        }

        return rules;
    }

    private List<JsonLogicCondition> parseConditions(JsonNode conditionNode) {
        List<JsonLogicCondition> conditions = new ArrayList<>();

        if (conditionNode.has("and")) {
            for (JsonNode cond : conditionNode.get("and")) {
                conditions.add(parseCondition(cond));
            }
        } else {
            conditions.add(parseCondition(conditionNode));
        }

        return conditions;
    }

    private JsonLogicCondition parseCondition(JsonNode condNode) {
        String op = condNode.fieldNames().next();
        ArrayNode args = (ArrayNode) condNode.get(op);

        String path = args.get(0).asText();
        String value = args.get(1).asText();

        String variable = extractVariableName(path);

        return new JsonLogicCondition(variable, op, value);
    }

    private String extractVariableName(String path) {
        String[] parts = path.split("\\.");
        // В случае "answer.c2.value" → вернёт "c2"
        if (parts.length >= 2 && "value".equals(parts[2])) {
            return parts[1];
        } else if (parts.length > 0) {
            return parts[parts.length - 1];
        }
        return path;
    }

    private <T> List<List<T>> cartesianProduct(List<List<T>> lists) {
        List<List<T>> result = List.of(List.of());

        for (List<T> list : lists) {
            result = result.stream()
                    .flatMap(prev -> list.stream().map(elem -> {
                        List<T> next = new ArrayList<>(prev);
                        next.add(elem);
                        return next;
                    }))
                    .collect(Collectors.toList());
        }
        return result;
    }

}
