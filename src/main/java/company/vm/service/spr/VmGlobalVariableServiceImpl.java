package company.vm.service.spr;


import company.vm.dto.service.spr.VmGlobalVariableDto;
import company.vm.dto.service.spr.VmGlobalVariableIdDto;
import company.vm.entity.VmGlobalVariableEntity;
import company.vm.exception.VmGlobalVariableNotFoundException;
import company.vm.mapper.VmGlobalVariableMapper;
import company.vm.repository.VmGlobalVariableRepository;
import company.vm.service.VmGlobalVariableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class VmGlobalVariableServiceImpl implements VmGlobalVariableService {

    private final VmGlobalVariableRepository vmGlobalVariableRepository;
    private final VmGlobalVariableMapper vmGlobalVariableMapper;

    public VmGlobalVariableServiceImpl(VmGlobalVariableRepository vmGlobalVariableRepository,
                                       VmGlobalVariableMapper vmGlobalVariableMapper
    ) {
        this.vmGlobalVariableRepository = vmGlobalVariableRepository;
        this.vmGlobalVariableMapper = vmGlobalVariableMapper;
    }

    @Override
    @Transactional
    public VmGlobalVariableIdDto createGlobalVariable(VmGlobalVariableDto dto) {
        VmGlobalVariableEntity vmGlobalVariableEntity = vmGlobalVariableMapper.toEntity(dto);
        VmGlobalVariableEntity savedEntity = vmGlobalVariableRepository.save(vmGlobalVariableEntity);
        return vmGlobalVariableMapper.toDto(savedEntity);
    }

    @Override
    public VmGlobalVariableIdDto getGlobalVariableById(Integer id) {
        VmGlobalVariableEntity vmGlobalVariableEntity = vmGlobalVariableRepository
                .findById(id)
                .orElseThrow(() -> new VmGlobalVariableNotFoundException(id));

        return vmGlobalVariableMapper.toDto(vmGlobalVariableEntity);
    }

    @Override
    @Transactional
    public VmGlobalVariableIdDto updateGlobalVariable(Integer id, VmGlobalVariableDto dto) {
        VmGlobalVariableEntity vmGlobalVariableEntity = vmGlobalVariableRepository
                .findById(id)
                .orElseThrow(() -> new VmGlobalVariableNotFoundException(id));

        vmGlobalVariableEntity.setName(dto.name());
        vmGlobalVariableEntity.setDescription(dto.description());
        VmGlobalVariableEntity updatedEntity = vmGlobalVariableRepository.save(vmGlobalVariableEntity);
        return vmGlobalVariableMapper.toDto(updatedEntity);
    }

    @Override
    public List<VmGlobalVariableIdDto> getGlobalVariables() {
        return vmGlobalVariableRepository
                .findAll()
                .stream()
                .map(vmGlobalVariableMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteGlobalVariable(Integer id) {
        VmGlobalVariableEntity vmGlobalVariableEntity = vmGlobalVariableRepository
                .findById(id)
                .orElseThrow(() -> new VmGlobalVariableNotFoundException(id));

        vmGlobalVariableRepository.delete(vmGlobalVariableEntity);
    }
}
