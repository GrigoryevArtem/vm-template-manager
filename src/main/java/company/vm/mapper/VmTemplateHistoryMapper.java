package company.vm.mapper;


import company.vm.dto.controller.VmTemplateHistoryRequestDto;
import company.vm.dto.controller.VmTemplateHistoryResponseDto;
import company.vm.dto.service.template.VmTemplateHistoryDto;
import company.vm.dto.service.template.VmTemplateHistoryServiceDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Mapper(componentModel = "spring")
public interface VmTemplateHistoryMapper {

    VmTemplateHistoryResponseDto toControllerDto(VmTemplateHistoryDto dto);

    List<VmTemplateHistoryResponseDto> toResponseList(List<VmTemplateHistoryDto> list);

    default VmTemplateHistoryServiceDto toServiceDto(VmTemplateHistoryRequestDto dto, MultipartFile file) {
        return new VmTemplateHistoryServiceDto(
                dto.name(),
                dto.description(),
                dto.versionId(),
                dto.type(),
                file
        );
    }

    default Page<VmTemplateHistoryResponseDto> toResponsePage(Page<VmTemplateHistoryDto> page) {
        List<VmTemplateHistoryResponseDto> content = toResponseList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }
}
