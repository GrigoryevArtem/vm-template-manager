package company.vm.mapper;


import company.vm.dto.controller.VmTemplateSchemaTypeRequestDto;
import company.vm.dto.controller.VmTemplateSchemaTypeResponseDto;
import company.vm.dto.service.template.VmTemplateSchemaTypeDto;
import company.vm.dto.service.template.VmTemplateSchemaTypeIdDto;
import company.vm.entity.VmTemplateSchemaTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface VmTemplateSchemaTypeMapper {
    @Mapping(target = "id", ignore = true)
    VmTemplateSchemaTypeEntity toEntity(VmTemplateSchemaTypeDto dto);

    VmTemplateSchemaTypeEntity toEntity(VmTemplateSchemaTypeIdDto dto);

    VmTemplateSchemaTypeIdDto toDto(VmTemplateSchemaTypeEntity entity);

    VmTemplateSchemaTypeDto toServiceDto(VmTemplateSchemaTypeRequestDto dto);

    VmTemplateSchemaTypeResponseDto toControllerDto(VmTemplateSchemaTypeIdDto dto);

    List<VmTemplateSchemaTypeResponseDto> toControllerDtoList(List<VmTemplateSchemaTypeIdDto> dtoList);
}
