package company.vm.mapper;


import company.vm.dto.controller.VmTemplateSchemaResponseDto;
import company.vm.dto.service.template.VmTemplateSchemaDto;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface VmTemplateSchemaMapper {

    VmTemplateSchemaResponseDto toControllerDto(VmTemplateSchemaDto dto);

    List<VmTemplateSchemaResponseDto> toResponseList(List<VmTemplateSchemaDto> list);

}
