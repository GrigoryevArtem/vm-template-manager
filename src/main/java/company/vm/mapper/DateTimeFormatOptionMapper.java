package company.vm.mapper;


import company.vm.dto.controller.DateTimeFormatOptionRequestDto;
import company.vm.dto.controller.DateTimeFormatOptionResponseDto;
import company.vm.dto.service.spr.DateTimeFormatOptionDto;
import company.vm.dto.service.spr.DateTimeFormatOptionIdDto;
import company.vm.entity.DateTimeFormatOptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface DateTimeFormatOptionMapper {

    @Mapping(target = "id", ignore = true)
    DateTimeFormatOptionEntity toEntity(DateTimeFormatOptionDto dto);

    DateTimeFormatOptionIdDto toDto(DateTimeFormatOptionEntity entity);

    DateTimeFormatOptionDto toServiceDto(DateTimeFormatOptionRequestDto dto);

    DateTimeFormatOptionResponseDto toControllerDto(DateTimeFormatOptionIdDto dto);

    List<DateTimeFormatOptionResponseDto> toControllerDtoList(List<DateTimeFormatOptionIdDto> dtoList);
}
