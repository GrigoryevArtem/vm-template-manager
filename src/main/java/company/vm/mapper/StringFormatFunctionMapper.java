package company.vm.mapper;


import company.vm.dto.controller.StringFormatFunctionRequestDto;
import company.vm.dto.controller.StringFormatFunctionResponseDto;
import company.vm.dto.service.spr.StringFormatFunctionDto;
import company.vm.dto.service.spr.StringFormatFunctionIdDto;
import company.vm.entity.StringFormatFunctionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface StringFormatFunctionMapper {

    @Mapping(target = "id", ignore = true)
    StringFormatFunctionEntity toEntity(StringFormatFunctionDto dto);

    StringFormatFunctionIdDto toDto(StringFormatFunctionEntity entity);

    StringFormatFunctionDto toServiceDto(StringFormatFunctionRequestDto dto);

    StringFormatFunctionResponseDto toControllerDto(StringFormatFunctionIdDto dto);

    List<StringFormatFunctionResponseDto> toControllerDtoList(List<StringFormatFunctionIdDto> dtoList);

}
