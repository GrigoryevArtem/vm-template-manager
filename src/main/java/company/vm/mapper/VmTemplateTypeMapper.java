package company.vm.mapper;


import company.vm.dto.controller.VmTemplateTypeRequestDto;
import company.vm.dto.controller.VmTemplateTypeResponseDto;
import company.vm.dto.service.template.VmTemplateTypeDto;
import company.vm.dto.service.template.VmTemplateTypeIdDto;
import company.vm.entity.VmTemplateTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface VmTemplateTypeMapper {
    @Mapping(target = "id", ignore = true)
    VmTemplateTypeEntity toEntity(VmTemplateTypeDto dto);

    VmTemplateTypeEntity toEntity(VmTemplateTypeIdDto dto);

    VmTemplateTypeIdDto toDto(VmTemplateTypeEntity entity);

    VmTemplateTypeDto toServiceDto(VmTemplateTypeRequestDto dto);

    VmTemplateTypeResponseDto toControllerDto(VmTemplateTypeIdDto dto);

    List<VmTemplateTypeResponseDto> toControllerDtoList(List<VmTemplateTypeIdDto> dtoList);
}
