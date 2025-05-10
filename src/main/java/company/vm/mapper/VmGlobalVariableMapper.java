package company.vm.mapper;


import company.vm.dto.controller.VmGlobalVariableRequestDto;
import company.vm.dto.controller.VmGlobalVariableResponseDto;
import company.vm.dto.service.spr.VmGlobalVariableDto;
import company.vm.dto.service.spr.VmGlobalVariableIdDto;
import company.vm.entity.VmGlobalVariableEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface VmGlobalVariableMapper {

    @Mapping(target = "id", ignore = true)
    VmGlobalVariableEntity toEntity(VmGlobalVariableDto dto);

    VmGlobalVariableIdDto toDto(VmGlobalVariableEntity entity);

    VmGlobalVariableDto toServiceDto(VmGlobalVariableRequestDto dto);

    VmGlobalVariableResponseDto toControllerDto(VmGlobalVariableIdDto dto);

    List<VmGlobalVariableResponseDto> toControllerDtoList(List<VmGlobalVariableIdDto> dtoList);
}
