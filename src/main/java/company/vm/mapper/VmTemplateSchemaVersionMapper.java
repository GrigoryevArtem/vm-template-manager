package company.vm.mapper;


import company.vm.dto.controller.VmTemplateSchemaVersionRequestDto;
import company.vm.dto.controller.VmTemplateSchemaVersionResponseDto;
import company.vm.dto.service.template.VmTemplateSchemaVersionDto;
import company.vm.dto.service.template.VmTemplateSchemaVersionIdDto;
import company.vm.entity.VmTemplateSchemaVersionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface VmTemplateSchemaVersionMapper {
    @Mapping(target = "id", ignore = true)
    VmTemplateSchemaVersionEntity toEntity(VmTemplateSchemaVersionDto dto);

    VmTemplateSchemaVersionEntity toEntity(VmTemplateSchemaVersionIdDto dto);

    VmTemplateSchemaVersionIdDto toDto(VmTemplateSchemaVersionEntity entity);

    VmTemplateSchemaVersionDto toServiceDto(VmTemplateSchemaVersionRequestDto dto);

    VmTemplateSchemaVersionResponseDto toControllerDto(VmTemplateSchemaVersionIdDto dto);

    List<VmTemplateSchemaVersionResponseDto> toControllerDtoList(List<VmTemplateSchemaVersionIdDto> dtoList);
}
