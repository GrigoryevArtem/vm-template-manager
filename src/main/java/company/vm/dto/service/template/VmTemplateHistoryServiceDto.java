package company.vm.dto.service.template;


import org.springframework.web.multipart.MultipartFile;


public record VmTemplateHistoryServiceDto(
        String name,
        String description,
        Integer versionId,
        Integer type,
        MultipartFile fileContent
) {
}
