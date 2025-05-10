package company.vm.mapper;


import company.vm.dto.controller.VmComponentRequestDto;
import company.vm.dto.controller.VmComponentResponseDto;
import company.vm.dto.service.spr.VmComponentDto;
import company.vm.dto.service.spr.VmComponentIdDto;
import company.vm.entity.VmComponentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface VmComponentMapper {

    @Mapping(target = "id", ignore = true)
    VmComponentEntity toEntity(VmComponentDto dto);

    VmComponentEntity toEntity(VmComponentIdDto dto);

    VmComponentIdDto toDto(VmComponentEntity entity);

    VmComponentDto toDto(VmComponentIdDto entity);

    VmComponentDto toServiceDto(VmComponentRequestDto dto);

    VmComponentResponseDto toControllerDto(VmComponentIdDto dto);

    List<VmComponentResponseDto> toControllerDtoList(List<VmComponentIdDto> dtoList);

}
