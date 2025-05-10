package company.vm.mapper;


import company.vm.dto.controller.PdfClassNameRequestDto;
import company.vm.dto.controller.PdfClassNameResponseDto;
import company.vm.dto.service.spr.PdfClassNameDto;
import company.vm.dto.service.spr.PdfClassNameIdDto;
import company.vm.entity.PdfClassNameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PdfClassNameMapper {

    @Mapping(target = "id", ignore = true)
    PdfClassNameEntity toEntity(PdfClassNameDto dto);

    PdfClassNameIdDto toDto(PdfClassNameEntity entity);

    PdfClassNameDto toServiceDto(PdfClassNameRequestDto dto);

    PdfClassNameResponseDto toControllerDto(PdfClassNameIdDto dto);

    List<PdfClassNameResponseDto> toControllerDtoList(List<PdfClassNameIdDto> dtoList);

}
