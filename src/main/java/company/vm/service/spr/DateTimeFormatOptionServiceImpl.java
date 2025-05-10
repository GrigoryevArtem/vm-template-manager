package company.vm.service.spr;


import company.vm.dto.service.spr.DateTimeFormatOptionDto;
import company.vm.dto.service.spr.DateTimeFormatOptionIdDto;
import company.vm.entity.DateTimeFormatOptionEntity;
import company.vm.exception.DateTimeFormatOptionNotFoundException;
import company.vm.mapper.DateTimeFormatOptionMapper;
import company.vm.repository.DateTimeFormatOptionRepository;
import company.vm.service.DateTimeFormatOptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class DateTimeFormatOptionServiceImpl implements DateTimeFormatOptionService {

    private final DateTimeFormatOptionRepository dateTimeFormatOptionRepository;
    private final DateTimeFormatOptionMapper dateTimeFormatOptionMapper;

    public DateTimeFormatOptionServiceImpl(DateTimeFormatOptionRepository dateTimeFormatOptionRepository,
                                           DateTimeFormatOptionMapper dateTimeFormatOptionMapper
    ) {
        this.dateTimeFormatOptionRepository = dateTimeFormatOptionRepository;
        this.dateTimeFormatOptionMapper = dateTimeFormatOptionMapper;
    }

    @Override
    @Transactional
    public DateTimeFormatOptionIdDto createDateTimeFormatOption(DateTimeFormatOptionDto dto) {
        DateTimeFormatOptionEntity entity = dateTimeFormatOptionMapper.toEntity(dto);
        DateTimeFormatOptionEntity createdEntity = dateTimeFormatOptionRepository.save(entity);
        return dateTimeFormatOptionMapper.toDto(createdEntity);
    }

    @Override
    public DateTimeFormatOptionIdDto getDateTimeFormatOptionById(Integer id) {
        DateTimeFormatOptionEntity entity = dateTimeFormatOptionRepository
                .findById(id)
                .orElseThrow(() -> new DateTimeFormatOptionNotFoundException(id));
        return dateTimeFormatOptionMapper.toDto(entity);
    }

    @Override
    @Transactional
    public DateTimeFormatOptionIdDto updateDateTimeFormatOption(Integer id, DateTimeFormatOptionDto dto) {
        DateTimeFormatOptionEntity entity = dateTimeFormatOptionRepository
                .findById(id)
                .orElseThrow(() -> new DateTimeFormatOptionNotFoundException(id));

        entity.setName(dto.name());
        entity.setFormatPattern(dto.formatPattern());
        DateTimeFormatOptionEntity updatedEntity = dateTimeFormatOptionRepository.save(entity);
        return dateTimeFormatOptionMapper.toDto(updatedEntity);
    }

    @Override
    public List<DateTimeFormatOptionIdDto> getDateTimeFormatOptions() {
        return dateTimeFormatOptionRepository
                .findAll()
                .stream()
                .map(dateTimeFormatOptionMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteDateTimeFormatOption(Integer id) {
        DateTimeFormatOptionEntity entity = dateTimeFormatOptionRepository
                .findById(id)
                .orElseThrow(() -> new DateTimeFormatOptionNotFoundException(id));

        dateTimeFormatOptionRepository.delete(entity);
    }
}
