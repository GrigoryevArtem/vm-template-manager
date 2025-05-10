package company.vm.service.spr;


import company.vm.dto.service.spr.VmComponentVariableDto;
import company.vm.dto.service.spr.VmComponentVariableIdDto;
import company.vm.entity.VmComponentVariableEntity;
import company.vm.exception.VmComponentVariableNotFoundException;
import company.vm.mapper.VmComponentVariableMapper;
import company.vm.repository.VmComponentVariableRepository;
import company.vm.service.VmComponentVariableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class VmComponentVariableServiceImpl implements VmComponentVariableService {

    private final VmComponentVariableRepository vmComponentVariableRepository;
    private final VmComponentVariableMapper vmComponentVariableMapper;

    public VmComponentVariableServiceImpl(VmComponentVariableRepository vmComponentVariableRepository,
                                          VmComponentVariableMapper vmComponentVariableMapper
    ) {
        this.vmComponentVariableRepository = vmComponentVariableRepository;
        this.vmComponentVariableMapper = vmComponentVariableMapper;
    }

    @Override
    @Transactional
    public VmComponentVariableIdDto createVmComponentVariable(VmComponentVariableDto dto) {
        VmComponentVariableEntity entity = vmComponentVariableMapper.toEntity(dto);
        VmComponentVariableEntity savedEntity = vmComponentVariableRepository.save(entity);
        return vmComponentVariableMapper.toDto(savedEntity);
    }

    @Override
    public VmComponentVariableIdDto getVmComponentVariableById(Integer id) {
        VmComponentVariableEntity entity = vmComponentVariableRepository
                .findById(id)
                .orElseThrow(() -> new VmComponentVariableNotFoundException(id));

        return vmComponentVariableMapper.toDto(entity);
    }

    @Override
    @Transactional
    public VmComponentVariableIdDto updateVmComponentVariable(Integer id, VmComponentVariableDto dto) {
        VmComponentVariableEntity entity = vmComponentVariableRepository
                .findById(id)
                .orElseThrow(() -> new VmComponentVariableNotFoundException(id));

        entity.setName(dto.name());
        entity.setDescription(dto.description());
        VmComponentVariableEntity updatedEntity = vmComponentVariableRepository.save(entity);
        return vmComponentVariableMapper.toDto(updatedEntity);
    }

    @Override
    public List<VmComponentVariableIdDto> getVmComponentVariables() {
        return vmComponentVariableRepository
                .findAll()
                .stream()
                .map(vmComponentVariableMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteVmComponentVariable(Integer id) {
        VmComponentVariableEntity entity = vmComponentVariableRepository
                .findById(id)
                .orElseThrow(() -> new VmComponentVariableNotFoundException(id));

        vmComponentVariableRepository.delete(entity);
    }
}
