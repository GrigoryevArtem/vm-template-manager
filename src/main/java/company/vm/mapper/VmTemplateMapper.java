package company.vm.mapper;


import company.vm.dto.controller.VmTemplateRequestDto;
import company.vm.dto.controller.VmTemplateResponseDto;
import company.vm.dto.service.template.VmTemplateDto;
import company.vm.dto.service.template.VmTemplateServiceDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Mapper(componentModel = "spring")
public interface VmTemplateMapper {
    VmTemplateResponseDto toControllerDto(VmTemplateDto dto);

    List<VmTemplateResponseDto> toResponseList(List<VmTemplateDto> list);

    default VmTemplateServiceDto toServiceDto(VmTemplateRequestDto dto, MultipartFile file) {
        return new VmTemplateServiceDto(
                dto.name(),
                dto.description(),
                dto.versionId(),
                dto.type(),
                file
        );
    }

    default Page<VmTemplateResponseDto> toResponsePage(Page<VmTemplateDto> page) {
        List<VmTemplateResponseDto> content = toResponseList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

}
