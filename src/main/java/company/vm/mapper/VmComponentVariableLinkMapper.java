package company.vm.mapper;


import company.vm.dto.controller.VmComponentVariableLinkRequestDto;
import company.vm.dto.controller.VmComponentVariableLinkResponseDto;
import company.vm.dto.service.spr.VmComponentVariableLinkDto;
import company.vm.dto.service.spr.VmComponentVariableLinkIdDto;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface VmComponentVariableLinkMapper {

    VmComponentVariableLinkDto toServiceDto(VmComponentVariableLinkRequestDto dto);

    VmComponentVariableLinkDto toServiceDto(VmComponentVariableLinkIdDto dto);

    VmComponentVariableLinkResponseDto toControllerDto(VmComponentVariableLinkIdDto dto);

    List<VmComponentVariableLinkResponseDto> toControllerDtoList(List<VmComponentVariableLinkIdDto> dtoList);

}
