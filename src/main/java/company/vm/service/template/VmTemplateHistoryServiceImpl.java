package company.vm.service.template;


import company.vm.dto.service.template.*;
import company.vm.entity.VmTemplateHistoryEntity;
import company.vm.entity.VmTemplateSchemaVersionEntity;
import company.vm.entity.VmTemplateTypeEntity;
import company.vm.exception.VmTemplateHistoryNotFoundException;
import company.vm.mapper.VmTemplateSchemaVersionMapper;
import company.vm.mapper.VmTemplateTypeMapper;
import company.vm.repository.VmTemplateHistoryRepository;
import company.vm.service.VmTemplateHistoryService;
import company.vm.service.VmTemplateSchemaVersionService;
import company.vm.service.VmTemplateTypeService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.List;


@Service
public class VmTemplateHistoryServiceImpl implements VmTemplateHistoryService {

    private final VmTemplateHistoryRepository vmTemplateHistoryRepository;

    private final VmTemplateSchemaVersionService vmTemplateSchemaVersionService;
    private final VmTemplateSchemaVersionMapper vmTemplateSchemaVersionMapper;

    private final VmTemplateTypeService vmTemplateTypeService;
    private final VmTemplateTypeMapper vmTemplateTypeMapper;

    public VmTemplateHistoryServiceImpl(VmTemplateHistoryRepository vmTemplateHistoryRepository,
                                        VmTemplateSchemaVersionService vmTemplateSchemaVersionService,
                                        VmTemplateSchemaVersionMapper vmTemplateSchemaVersionMapper,
                                        VmTemplateTypeService vmTemplateTypeService,
                                        VmTemplateTypeMapper vmTemplateTypeMapper
    ) {
        this.vmTemplateHistoryRepository = vmTemplateHistoryRepository;
        this.vmTemplateSchemaVersionService = vmTemplateSchemaVersionService;
        this.vmTemplateSchemaVersionMapper = vmTemplateSchemaVersionMapper;
        this.vmTemplateTypeService = vmTemplateTypeService;
        this.vmTemplateTypeMapper = vmTemplateTypeMapper;
    }

    @Override
    @Transactional
    public VmTemplateHistoryDto saveVmTemplateHistory(VmTemplateHistoryServiceDto dto) throws IOException {
        VmTemplateSchemaVersionIdDto versionIdDto = vmTemplateSchemaVersionService.getVmTemplateSchemaVersionById(dto.versionId());
        VmTemplateSchemaVersionEntity versionEntity = vmTemplateSchemaVersionMapper.toEntity(versionIdDto);
        VmTemplateTypeIdDto vmTemplateTypeIdDto = vmTemplateTypeService.getVmTemplateTypeById(dto.type());
        VmTemplateTypeEntity vmTemplateTypeEntity = vmTemplateTypeMapper.toEntity(vmTemplateTypeIdDto);

        VmTemplateHistoryEntity entity = toEntity(dto, versionEntity, vmTemplateTypeEntity);
        VmTemplateHistoryEntity savedEntity = vmTemplateHistoryRepository.save(entity);
        return toDto(savedEntity);
    }

    @NotNull
    private VmTemplateHistoryDto toDto(VmTemplateHistoryEntity savedEntity) {
        return new VmTemplateHistoryDto(
                savedEntity.getId(),
                savedEntity.getName(),
                savedEntity.getDescription(),
                savedEntity.getVersion().getName(),
                savedEntity.getType().getName(),
                savedEntity.getModifiedAt(),
                savedEntity.getModifiedAt()
        );
    }

    private static VmTemplateHistoryEntity toEntity(VmTemplateHistoryServiceDto dto,
                                                    VmTemplateSchemaVersionEntity versionEntity,
                                                    VmTemplateTypeEntity typeEntity) throws IOException {
        VmTemplateHistoryEntity entity = new VmTemplateHistoryEntity();
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setVersion(versionEntity);
        entity.setType(typeEntity);
        entity.setFileContent(dto.fileContent().getBytes());
        entity.setFileFormat(dto.fileContent().getContentType());
        entity.setUploadedAt(Instant.now());
        entity.setModifiedAt(Instant.now());
        return entity;
    }

    @Override
    public VmTemplateHistoryFileDto downloadVmTemplateHistoryById(Integer id) {
        VmTemplateHistoryEntity template = vmTemplateHistoryRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateHistoryNotFoundException(id));

        return new VmTemplateHistoryFileDto(template.getName(), template.getFileFormat(), template.getFileContent());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VmTemplateHistoryDto> getPreviewVmTemplateHistories(Pageable pageRequest) {
        Page<VmTemplateHistoryEntity> templates = vmTemplateHistoryRepository.findAll(pageRequest);
        List<VmTemplateHistoryDto> dtos = templates.stream()
                .map(this::toDto)
                .toList();
        return new PageImpl<>(dtos, templates.getPageable(), templates.getTotalElements());
    }

    @Override
    @Transactional
    public VmTemplateHistoryDto updateVmTemplateHistory(Integer id, VmTemplateHistoryServiceDto dto) throws IOException {
        VmTemplateHistoryEntity templateHistory = vmTemplateHistoryRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateHistoryNotFoundException(id));

        VmTemplateSchemaVersionIdDto versionIdDto = vmTemplateSchemaVersionService.getVmTemplateSchemaVersionById(dto.versionId());
        VmTemplateSchemaVersionEntity versionEntity = vmTemplateSchemaVersionMapper.toEntity(versionIdDto);
        templateHistory.setName(dto.name());
        templateHistory.setDescription(dto.description());
        templateHistory.setVersion(versionEntity);
        templateHistory.setFileContent(dto.fileContent().getBytes());
        templateHistory.setFileFormat(dto.fileContent().getContentType());
        templateHistory.setModifiedAt(Instant.now());

        VmTemplateHistoryEntity savedEntity = vmTemplateHistoryRepository.save(templateHistory);
        return toDto(savedEntity);
    }

    @Override
    @Transactional
    public void deleteVmTemplateHistory(Integer id) {
        VmTemplateHistoryEntity templateHistory = vmTemplateHistoryRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateHistoryNotFoundException(id));

        vmTemplateHistoryRepository.delete(templateHistory);
    }
}
