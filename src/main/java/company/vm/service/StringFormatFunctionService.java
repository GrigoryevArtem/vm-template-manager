package company.vm.service;


import company.vm.dto.service.spr.StringFormatFunctionDto;
import company.vm.dto.service.spr.StringFormatFunctionIdDto;

import java.util.List;


public interface StringFormatFunctionService {

    StringFormatFunctionIdDto createStringFormatFunction(StringFormatFunctionDto dto);

    StringFormatFunctionIdDto getStringFormatFunctionById(Integer id);

    StringFormatFunctionIdDto updateStringFormatFunction(Integer id, StringFormatFunctionDto dto);

    List<StringFormatFunctionIdDto> getStringFormatFunctions();

    void deleteStringFormatFunction(Integer id);

}
