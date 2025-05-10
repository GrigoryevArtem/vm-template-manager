package company.vm.dto.service.template;


import org.springframework.web.multipart.MultipartFile;


public record VmTemplateSchemaUpdateDto(
        Integer versionId,
        MultipartFile file
) {
}
