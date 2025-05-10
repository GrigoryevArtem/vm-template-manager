package company.vm.service.template;


import company.vm.dto.service.template.VmTemplateSchemaTypeDto;
import company.vm.dto.service.template.VmTemplateSchemaTypeIdDto;
import company.vm.entity.VmTemplateSchemaTypeEntity;
import company.vm.exception.VmTemplateSchemaTypeNotFoundException;
import company.vm.mapper.VmTemplateSchemaTypeMapper;
import company.vm.repository.VmTemplateSchemaTypeRepository;
import company.vm.service.VmTemplateSchemaTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class VmTemplateSchemaTypeServiceImpl implements VmTemplateSchemaTypeService {

    private final VmTemplateSchemaTypeRepository vmTemplateSchemaTypeRepository;
    private final VmTemplateSchemaTypeMapper vmTemplateSchemaTypeMapper;

    public VmTemplateSchemaTypeServiceImpl(VmTemplateSchemaTypeRepository vmTemplateSchemaTypeRepository,
                                           VmTemplateSchemaTypeMapper vmTemplateSchemaTypeMapper
    ) {
        this.vmTemplateSchemaTypeRepository = vmTemplateSchemaTypeRepository;
        this.vmTemplateSchemaTypeMapper = vmTemplateSchemaTypeMapper;
    }

    @Override
    @Transactional
    public VmTemplateSchemaTypeIdDto createVmTemplateSchemaType(VmTemplateSchemaTypeDto dto) {
        VmTemplateSchemaTypeEntity entity = vmTemplateSchemaTypeMapper.toEntity(dto);
        VmTemplateSchemaTypeEntity createdEntity = vmTemplateSchemaTypeRepository.save(entity);
        return vmTemplateSchemaTypeMapper.toDto(createdEntity);
    }

    @Override
    public VmTemplateSchemaTypeIdDto getVmTemplateSchemaTypeById(Integer id) {
        VmTemplateSchemaTypeEntity entity = vmTemplateSchemaTypeRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateSchemaTypeNotFoundException(id));
        return vmTemplateSchemaTypeMapper.toDto(entity);
    }

    @Override
    public VmTemplateSchemaTypeIdDto getVmTemplateSchemaTypeByName(String name) {
        VmTemplateSchemaTypeEntity entity = vmTemplateSchemaTypeRepository
                .findByName(name)
                .orElseThrow(() -> new VmTemplateSchemaTypeNotFoundException(
                        "Схема шаблона с именем '%s' не найдена".formatted(name)
                ));
        return vmTemplateSchemaTypeMapper.toDto(entity);
    }

    @Override
    @Transactional
    public VmTemplateSchemaTypeIdDto updateVmTemplateSchemaType(Integer id, VmTemplateSchemaTypeDto dto) {
        VmTemplateSchemaTypeEntity entity = vmTemplateSchemaTypeRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateSchemaTypeNotFoundException(id));
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        VmTemplateSchemaTypeEntity updatedEntity = vmTemplateSchemaTypeRepository.save(entity);
        return vmTemplateSchemaTypeMapper.toDto(updatedEntity);
    }

    @Override
    public List<VmTemplateSchemaTypeIdDto> getVmTemplateSchemaTypes() {
        return vmTemplateSchemaTypeRepository
                .findAll()
                .stream()
                .map(vmTemplateSchemaTypeMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteVmTemplateSchemaType(Integer id) {
        VmTemplateSchemaTypeEntity entity = vmTemplateSchemaTypeRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateSchemaTypeNotFoundException(id));

        vmTemplateSchemaTypeRepository.delete(entity);
    }
}
