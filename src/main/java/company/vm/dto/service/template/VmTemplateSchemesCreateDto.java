package company.vm.dto.service.template;


import org.springframework.web.multipart.MultipartFile;


public record VmTemplateSchemesCreateDto(
        Integer versionId,
        MultipartFile basicSchema,
        MultipartFile nestedSchema
) {
}
