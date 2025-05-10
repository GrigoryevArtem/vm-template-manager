package company.vm.service;


import company.vm.dto.service.spr.DateTimeFormatOptionDto;
import company.vm.dto.service.spr.DateTimeFormatOptionIdDto;

import java.util.List;


public interface DateTimeFormatOptionService {
    DateTimeFormatOptionIdDto createDateTimeFormatOption(DateTimeFormatOptionDto dto);

    DateTimeFormatOptionIdDto getDateTimeFormatOptionById(Integer id);

    DateTimeFormatOptionIdDto updateDateTimeFormatOption(Integer id, DateTimeFormatOptionDto dto);

    List<DateTimeFormatOptionIdDto> getDateTimeFormatOptions();

    void deleteDateTimeFormatOption(Integer id);
}
