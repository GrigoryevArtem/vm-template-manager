package company.vm.dto.service.template;


public record VmTemplateSchemaFileDto(
        String name,
        byte[] fileContent
) {
}
