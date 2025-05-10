package company.vm.service.spr;


import company.vm.dto.service.spr.VmComponentDto;
import company.vm.dto.service.spr.VmComponentIdDto;
import company.vm.entity.VmComponentEntity;
import company.vm.exception.VmComponentNotFoundException;
import company.vm.mapper.VmComponentMapper;
import company.vm.repository.VmComponentRepository;
import company.vm.service.VmComponentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class VmComponentServiceImpl implements VmComponentService {

    private final VmComponentRepository vmComponentRepository;
    private final VmComponentMapper vmComponentMapper;

    public VmComponentServiceImpl(VmComponentRepository vmComponentRepository,
                                  VmComponentMapper vmComponentMapper
    ) {
        this.vmComponentRepository = vmComponentRepository;
        this.vmComponentMapper = vmComponentMapper;
    }

    @Override
    @Transactional
    public VmComponentIdDto createVmComponent(VmComponentDto dto) {
        VmComponentEntity vmComponentEntity = vmComponentMapper.toEntity(dto);
        VmComponentEntity createdEntity = vmComponentRepository.save(vmComponentEntity);
        return vmComponentMapper.toDto(createdEntity);
    }

    @Override
    public VmComponentIdDto getVmComponentById(Integer id) {
        VmComponentEntity vmComponentEntity = vmComponentRepository
                .findById(id)
                .orElseThrow(() -> new VmComponentNotFoundException(id));
        return vmComponentMapper.toDto(vmComponentEntity);
    }

    @Override
    @Transactional
    public VmComponentIdDto updateVmComponent(Integer id, VmComponentDto dto) {
        VmComponentEntity vmComponentEntity = vmComponentRepository
                .findById(id)
                .orElseThrow(() -> new VmComponentNotFoundException(id));
        vmComponentEntity.setName(dto.name());
        vmComponentEntity.setDescription(dto.description());
        VmComponentEntity updatedEntity = vmComponentRepository.save(vmComponentEntity);
        return vmComponentMapper.toDto(updatedEntity);
    }

    @Override
    public List<VmComponentIdDto> getVmComponents() {
        return vmComponentRepository
                .findAll()
                .stream()
                .map(vmComponentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteVmComponent(Integer id) {
        VmComponentEntity vmComponentEntity = vmComponentRepository
                .findById(id)
                .orElseThrow(() -> new VmComponentNotFoundException(id));
        vmComponentRepository.delete(vmComponentEntity);
    }
}
