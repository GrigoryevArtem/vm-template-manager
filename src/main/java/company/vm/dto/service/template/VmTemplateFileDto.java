package company.vm.dto.service.template;


public record VmTemplateFileDto(
        String fileName,
        String contentType,
        byte[] fileContent
) {
}
