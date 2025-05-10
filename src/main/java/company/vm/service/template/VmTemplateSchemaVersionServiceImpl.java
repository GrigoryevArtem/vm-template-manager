package company.vm.service.template;


import company.vm.dto.service.template.VmTemplateSchemaVersionDto;
import company.vm.dto.service.template.VmTemplateSchemaVersionIdDto;
import company.vm.entity.VmTemplateSchemaVersionEntity;
import company.vm.exception.VmTemplateSchemaVersionNotFoundException;
import company.vm.mapper.VmTemplateSchemaVersionMapper;
import company.vm.repository.VmTemplateSchemaVersionRepository;
import company.vm.service.VmTemplateSchemaVersionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class VmTemplateSchemaVersionServiceImpl implements VmTemplateSchemaVersionService {

    private final VmTemplateSchemaVersionRepository vmTemplateSchemaVersionRepository;
    private final VmTemplateSchemaVersionMapper vmTemplateSchemaVersionMapper;

    public VmTemplateSchemaVersionServiceImpl(VmTemplateSchemaVersionRepository vmTemplateSchemaVersionRepository,
                                              VmTemplateSchemaVersionMapper vmTemplateSchemaVersionMapper
    ) {
        this.vmTemplateSchemaVersionRepository = vmTemplateSchemaVersionRepository;
        this.vmTemplateSchemaVersionMapper = vmTemplateSchemaVersionMapper;
    }

    @Override
    @Transactional
    public VmTemplateSchemaVersionIdDto createVmTemplateSchemaVersion(VmTemplateSchemaVersionDto dto) {
        VmTemplateSchemaVersionEntity entity = vmTemplateSchemaVersionMapper.toEntity(dto);
        VmTemplateSchemaVersionEntity createdEntity = vmTemplateSchemaVersionRepository.save(entity);
        return vmTemplateSchemaVersionMapper.toDto(createdEntity);
    }

    @Override
    public VmTemplateSchemaVersionIdDto getVmTemplateSchemaVersionById(Integer id) {
        VmTemplateSchemaVersionEntity entity = vmTemplateSchemaVersionRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateSchemaVersionNotFoundException(id));
        return vmTemplateSchemaVersionMapper.toDto(entity);
    }

    @Override
    @Transactional
    public VmTemplateSchemaVersionIdDto updateVmTemplateSchemaVersion(Integer id, VmTemplateSchemaVersionDto dto) {
        VmTemplateSchemaVersionEntity entity = vmTemplateSchemaVersionRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateSchemaVersionNotFoundException(id));

        entity.setName(dto.name());
        VmTemplateSchemaVersionEntity savedEntity = vmTemplateSchemaVersionRepository.save(entity);
        return vmTemplateSchemaVersionMapper.toDto(savedEntity);
    }

    @Override
    public List<VmTemplateSchemaVersionIdDto> getVmTemplateSchemaVersions() {
        return vmTemplateSchemaVersionRepository
                .findAll()
                .stream()
                .map(vmTemplateSchemaVersionMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteVmTemplateSchemaVersion(Integer id) {
        VmTemplateSchemaVersionEntity entity = vmTemplateSchemaVersionRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateSchemaVersionNotFoundException(id));

        vmTemplateSchemaVersionRepository.delete(entity);
    }
}
