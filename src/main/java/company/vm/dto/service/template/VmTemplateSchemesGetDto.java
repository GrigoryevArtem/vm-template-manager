package company.vm.dto.service.template;


public record VmTemplateSchemesGetDto(
        VmTemplateSchemaFileDto basicSchema,
        VmTemplateSchemaFileDto nestedSchema
) {
}
