package company.vm.dto.service.template;


import org.springframework.web.multipart.MultipartFile;


public record VmTemplateServiceDto(
        String name,
        String description,
        Integer versionId,
        Integer type,
        MultipartFile fileContent
) {
}
