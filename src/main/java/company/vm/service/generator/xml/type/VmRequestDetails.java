package company.vm.service.generator.xml.type;


import com.fasterxml.jackson.databind.JsonNode;
import company.vm.dto.service.xml.VmTagSimpleStructure;
import company.vm.service.generator.xml.builder.ConditionalBlockBuilder;
import company.vm.service.generator.xml.builder.JsonLogicBlockBuilder;
import company.vm.service.generator.xml.builder.VmTagStructureBuilder;
import company.vm.service.generator.xml.decorator.VmConditionalDecorator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class VmRequestDetails {
    private static final Map<String, VmTagSimpleStructure> VM_REQUEST_DETAILS_MAP = new HashMap<>();

    // todo: тестовая версия жестко зашитых данных, которые не изменяются в шаблонах
    static {
        VM_REQUEST_DETAILS_MAP.put("Origination", new VmTagSimpleStructure(null, "epgu"));
        VM_REQUEST_DETAILS_MAP.put("Type", new VmTagSimpleStructure("10", "на назначение"));
        VM_REQUEST_DETAILS_MAP.put("TargetOrganization", new VmTagSimpleStructure("$%s['attributeValues']['CODE']", "$%s['title'].replaceAll(\"[(«»)]\", \" \")"));
        VM_REQUEST_DETAILS_MAP.put("Service", new VmTagSimpleStructure("$serviceId", "Предоставление мер социальной поддержки лицам, имеющим продолжительный стаж работы"));
    }

    //если пришел ApplicationInfo

    public StringBuilder execute(JsonNode regionServiceInfo) {

        StringBuilder builder = new StringBuilder();

        boolean isFilled = regionServiceInfo.get("isFilled").asBoolean();

        if (!isFilled && (regionServiceInfo.get("name") == null || !VM_REQUEST_DETAILS_MAP.containsKey(regionServiceInfo.get("name").asText()))) {
            return builder;
        }

        String componentName = regionServiceInfo.get("name").asText();
        String componentNamespace = regionServiceInfo.get("namespace").asText();

        if (componentName.equals("Entry")) {
            StringBuilder b = entryGenerated(regionServiceInfo);
            builder.append(b);
        }

        VmTagStructureBuilder vmTagStructureBuilder = new VmTagStructureBuilder.Builder(componentNamespace, componentName).build();

        // Если есть предустановленные данные в VM_REQUEST_DETAILS_MAP, добавляем их
        if (VM_REQUEST_DETAILS_MAP.containsKey(componentName)) {
            VmTagSimpleStructure vmTagSimpleStructure = VM_REQUEST_DETAILS_MAP.get(componentName);
            vmTagStructureBuilder.setCode(vmTagSimpleStructure.code());
            vmTagStructureBuilder.setContent(vmTagSimpleStructure.value());
        } else {
            vmTagStructureBuilder.setCode(UUID.randomUUID().toString());
        }

        String openTag = vmTagStructureBuilder.getOpenTag();
        builder.append(openTag);

        JsonNode childrenNode = regionServiceInfo.get("children");
        if (childrenNode != null && childrenNode.isArray() && !childrenNode.isEmpty()) {
            for (JsonNode child : childrenNode) {
                StringBuilder childBuilder = execute(child);
                if (!childBuilder.isEmpty()) {
                    builder.append(execute(child));
                }
            }
        } else {
            JsonNode linkedComponents = regionServiceInfo.get("linkedComponents");
            Optional.ofNullable(linkedComponents)
                    .filter(JsonNode::isArray)
                    .ifPresent(array -> {
                        String joinedIds = StreamSupport.stream(array.spliterator(), false)
                                .map(node -> "$" + node.get("id").asText())
                                .collect(Collectors.joining(" "));
                        builder.append(joinedIds);
                    });
        }

        String closeTag = vmTagStructureBuilder.getCloseTag();
        builder.append(closeTag);

        return builder;
    }

    private StringBuilder entryGenerated(JsonNode regionServiceInfo) {
        StringBuilder builder = new StringBuilder();
        Map<String, StringBuilder> builders = new HashMap<>();

        boolean isFilled = regionServiceInfo.get("isFilled").asBoolean();
        String componentName = regionServiceInfo.get("name").asText();
        String componentNamespace = regionServiceInfo.get("namespace").asText();

        if (!isFilled) {
            return builder;
        }

        JsonNode childrenNode = regionServiceInfo.get("children");
        if (childrenNode != null && childrenNode.isArray() && !childrenNode.isEmpty()) {
            for (JsonNode child : childrenNode) {
                if (!child.isEmpty()) {
                    isFilled = child.get("isFilled").asBoolean();
                    String componentChildName = child.get("name").asText();
                    String componentChildNamespace = child.get("namespace").asText();

                    VmTagStructureBuilder vmTagStructureBuilder = new VmTagStructureBuilder
                            .Builder(componentChildNamespace, componentChildName)
                            .build();

                    if (!isFilled) {
                        continue;
                    }

                    JsonNode linkedComponents = child.get("linkedComponents");
                    if (linkedComponents != null && linkedComponents.isArray()) {
                        for (JsonNode linkedComponent : linkedComponents) {
                            StringBuilder childBuilder = new StringBuilder();

                            String guid = UUID.randomUUID().toString();
                            vmTagStructureBuilder.setCode(guid);

                            String id = linkedComponent.get("id").asText();

                            if (componentChildName.equals("Title")) {
                                String title;
                                if (linkedComponent.get("label") != null) {
                                    title = linkedComponent.get("label").asText();
                                    vmTagStructureBuilder.setContent(title);
                                    childBuilder.append(vmTagStructureBuilder);
                                } else {
                                    JsonNode screen = linkedComponent.get("screen");
                                    title = screen.get("header").asText();
                                    if (title.contains("$")) {
                                        JsonLogicBlockBuilder jsonLogicBlockBuilder = new JsonLogicBlockBuilder();
                                        String value = jsonLogicBlockBuilder.convertToVelocity(screen, vmTagStructureBuilder);
                                        childBuilder.append(value);
                                    } else {
                                        vmTagStructureBuilder.setContent(title);
                                        childBuilder.append(vmTagStructureBuilder);
                                    }
                                }
                            } else {
                                JsonNode answers = linkedComponent.get("attributes").get("answers");
                                ConditionalBlockBuilder conditionalBlockBuilder = new ConditionalBlockBuilder(
                                        id, componentChildNamespace, componentChildName
                                );

                                String conditionalBlock = conditionalBlockBuilder.build(answers);
                                childBuilder.append(conditionalBlock);
                            }

                            if (builders.containsKey(id)) {
                                builders.get(id).append(childBuilder);
                            } else {
                                builders.put(id, childBuilder);
                            }
                        }
                    }
                }
            }
        }

        for (Map.Entry<String, StringBuilder> entry : builders.entrySet()) {
            VmTagStructureBuilder vmTagStructureBuilder = new VmTagStructureBuilder
                    .Builder(componentNamespace, componentName)
                    .withContent(entry.getValue().toString())
                    .build();
            String val = vmTagStructureBuilder.toString();

            VmConditionalDecorator vmConditionalDecorator = new VmConditionalDecorator(val, entry.getKey());
            String complexBuildString = vmConditionalDecorator.complexConditionBuild();
            builder.append(complexBuildString);
        }

        return builder;
    }
}
