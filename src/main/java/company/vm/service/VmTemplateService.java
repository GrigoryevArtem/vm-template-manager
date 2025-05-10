package company.vm.service;


import company.vm.dto.service.template.VmTemplateDto;
import company.vm.dto.service.template.VmTemplateFileDto;
import company.vm.dto.service.template.VmTemplateServiceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface VmTemplateService {
    VmTemplateFileDto generateXmlTemplate(MultipartFile file) throws IOException;

    VmTemplateFileDto generatePdfTemplate(String serviceNumber, MultipartFile file) throws IOException;

    VmTemplateDto saveTemplate(VmTemplateServiceDto dto) throws IOException;

    VmTemplateFileDto downloadTemplateById(Integer id);

    Page<VmTemplateDto> getPreviewTemplates(Pageable pageRequest);

    VmTemplateDto updateTemplate(Integer id, VmTemplateServiceDto dto) throws IOException;

    void deleteVmTemplate(Integer id);
}
