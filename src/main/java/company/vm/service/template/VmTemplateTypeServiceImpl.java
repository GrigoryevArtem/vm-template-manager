package company.vm.service.template;


import company.vm.dto.service.template.VmTemplateTypeDto;
import company.vm.dto.service.template.VmTemplateTypeIdDto;
import company.vm.entity.VmTemplateTypeEntity;
import company.vm.exception.VmTemplateTypeNotFoundException;
import company.vm.mapper.VmTemplateTypeMapper;
import company.vm.repository.VmTemplateTypeRepository;
import company.vm.service.VmTemplateTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class VmTemplateTypeServiceImpl implements VmTemplateTypeService {

    private final VmTemplateTypeRepository vmTemplateTypeRepository;
    private final VmTemplateTypeMapper vmTemplateTypeMapper;

    public VmTemplateTypeServiceImpl(VmTemplateTypeRepository vmTemplateTypeRepository, VmTemplateTypeMapper vmTemplateTypeMapper) {
        this.vmTemplateTypeRepository = vmTemplateTypeRepository;
        this.vmTemplateTypeMapper = vmTemplateTypeMapper;
    }

    @Override
    @Transactional
    public VmTemplateTypeIdDto createVmTemplateType(VmTemplateTypeDto dto) {
        VmTemplateTypeEntity entity = vmTemplateTypeMapper.toEntity(dto);
        VmTemplateTypeEntity savedEntity = vmTemplateTypeRepository.save(entity);
        return vmTemplateTypeMapper.toDto(savedEntity);
    }

    @Override
    public VmTemplateTypeIdDto getVmTemplateTypeById(Integer id) {
        VmTemplateTypeEntity entity = vmTemplateTypeRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateTypeNotFoundException(id));
        return vmTemplateTypeMapper.toDto(entity);
    }

    @Override
    @Transactional
    public VmTemplateTypeIdDto updateVmTemplateType(Integer id, VmTemplateTypeDto dto) {
        VmTemplateTypeEntity entity = vmTemplateTypeRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateTypeNotFoundException(id));
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        VmTemplateTypeEntity updatedEntity = vmTemplateTypeRepository.save(entity);
        return vmTemplateTypeMapper.toDto(updatedEntity);
    }

    @Override
    public List<VmTemplateTypeIdDto> getVmTemplateTypes() {
        return vmTemplateTypeRepository
                .findAll()
                .stream()
                .map(vmTemplateTypeMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteVmTemplateType(Integer id) {
        VmTemplateTypeEntity entity = vmTemplateTypeRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateTypeNotFoundException(id));

        vmTemplateTypeRepository.delete(entity);
    }
}
