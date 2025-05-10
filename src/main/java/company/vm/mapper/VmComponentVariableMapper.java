package company.vm.mapper;


import company.vm.dto.controller.VmComponentVariableRequestDto;
import company.vm.dto.controller.VmComponentVariableResponseDto;
import company.vm.dto.service.spr.VmComponentVariableDto;
import company.vm.dto.service.spr.VmComponentVariableIdDto;
import company.vm.entity.VmComponentVariableEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface VmComponentVariableMapper {

    @Mapping(target = "id", ignore = true)
    VmComponentVariableEntity toEntity(VmComponentVariableDto dto);

    VmComponentVariableEntity toEntity(VmComponentVariableIdDto dto);

    VmComponentVariableIdDto toDto(VmComponentVariableEntity entity);

    VmComponentVariableDto toServiceDto(VmComponentVariableRequestDto dto);

    VmComponentVariableResponseDto toControllerDto(VmComponentVariableIdDto dto);

    List<VmComponentVariableResponseDto> toControllerDtoList(List<VmComponentVariableIdDto> dtoList);

}
