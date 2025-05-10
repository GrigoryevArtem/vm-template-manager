package company.vm.service.spr;


import company.vm.dto.service.spr.StringFormatFunctionDto;
import company.vm.dto.service.spr.StringFormatFunctionIdDto;
import company.vm.entity.StringFormatFunctionEntity;
import company.vm.exception.StringFormatFunctionNotFoundException;
import company.vm.mapper.StringFormatFunctionMapper;
import company.vm.repository.StringFormatFunctionRepository;
import company.vm.service.StringFormatFunctionService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StringFormatFunctionServiceImpl implements StringFormatFunctionService {

    private final StringFormatFunctionRepository stringFormatFunctionRepository;
    private final StringFormatFunctionMapper stringFormatFunctionMapper;

    public StringFormatFunctionServiceImpl(StringFormatFunctionRepository stringFormatFunctionRepository, StringFormatFunctionMapper stringFormatFunctionMapper) {
        this.stringFormatFunctionRepository = stringFormatFunctionRepository;
        this.stringFormatFunctionMapper = stringFormatFunctionMapper;
    }

    @Override
    public StringFormatFunctionIdDto createStringFormatFunction(StringFormatFunctionDto dto) {
        StringFormatFunctionEntity entity = stringFormatFunctionMapper.toEntity(dto);
        StringFormatFunctionEntity createdRepository = stringFormatFunctionRepository.save(entity);
        return stringFormatFunctionMapper.toDto(createdRepository);
    }

    @Override
    public StringFormatFunctionIdDto getStringFormatFunctionById(Integer id) {
        StringFormatFunctionEntity entity = stringFormatFunctionRepository
                .findById(id)
                .orElseThrow(() -> new StringFormatFunctionNotFoundException(id));

        return stringFormatFunctionMapper.toDto(entity);
    }

    @Override
    public StringFormatFunctionIdDto updateStringFormatFunction(Integer id, StringFormatFunctionDto dto) {
        StringFormatFunctionEntity entity = stringFormatFunctionRepository
                .findById(id)
                .orElseThrow(() -> new StringFormatFunctionNotFoundException(id));
        entity.setTagName(dto.tagName());
        entity.setFunction(dto.function());
        stringFormatFunctionRepository.save(entity);
        return stringFormatFunctionMapper.toDto(entity);
    }

    @Override
    public List<StringFormatFunctionIdDto> getStringFormatFunctions() {
        return stringFormatFunctionRepository
                .findAll()
                .stream()
                .map(stringFormatFunctionMapper::toDto)
                .toList();
    }

    @Override
    public void deleteStringFormatFunction(Integer id) {
        StringFormatFunctionEntity entity = stringFormatFunctionRepository
                .findById(id)
                .orElseThrow(() -> new StringFormatFunctionNotFoundException(id));

        stringFormatFunctionRepository.delete(entity);
    }
}
