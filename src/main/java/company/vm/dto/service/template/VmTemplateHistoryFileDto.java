package company.vm.dto.service.template;


public record VmTemplateHistoryFileDto(
        String fileName,
        String contentType,
        byte[] fileContent
) {
}
