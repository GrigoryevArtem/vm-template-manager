package company.vm.service.template;


import company.vm.dto.service.template.*;
import company.vm.entity.VmTemplateSchemaEntity;
import company.vm.entity.VmTemplateSchemaTypeEntity;
import company.vm.entity.VmTemplateSchemaVersionEntity;
import company.vm.enums.VmTemplateSchemaType;
import company.vm.exception.VmTemplateSchemaNotFoundException;
import company.vm.mapper.VmTemplateSchemaTypeMapper;
import company.vm.mapper.VmTemplateSchemaVersionMapper;
import company.vm.repository.VmTemplateSchemaRepository;
import company.vm.service.VmTemplateSchemaService;
import company.vm.service.VmTemplateSchemaTypeService;
import company.vm.service.VmTemplateSchemaVersionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;


@Service
public class VmTemplateSchemaServiceImpl implements VmTemplateSchemaService {

    private final VmTemplateSchemaRepository vmTemplateSchemaRepository;
    private final VmTemplateSchemaVersionService vmTemplateSchemaVersionService;
    private final VmTemplateSchemaVersionMapper vmTemplateSchemaVersionMapper;
    private final VmTemplateSchemaTypeService vmTemplateSchemaTypeService;
    private final VmTemplateSchemaTypeMapper vmTemplateSchemaTypeMapper;


    public VmTemplateSchemaServiceImpl(VmTemplateSchemaRepository vmTemplateSchemaRepository,
                                       VmTemplateSchemaVersionService vmTemplateSchemaVersionService,
                                       VmTemplateSchemaVersionMapper vmTemplateSchemaVersionMapper,
                                       VmTemplateSchemaTypeService vmTemplateSchemaTypeService,
                                       VmTemplateSchemaTypeMapper vmTemplateSchemaTypeMapper
    ) {
        this.vmTemplateSchemaRepository = vmTemplateSchemaRepository;
        this.vmTemplateSchemaVersionService = vmTemplateSchemaVersionService;
        this.vmTemplateSchemaVersionMapper = vmTemplateSchemaVersionMapper;
        this.vmTemplateSchemaTypeService = vmTemplateSchemaTypeService;
        this.vmTemplateSchemaTypeMapper = vmTemplateSchemaTypeMapper;
    }

    @Override
    @Transactional
    public List<VmTemplateSchemaDto> createVmTemplateSchemas(VmTemplateSchemesCreateDto dto) throws IOException {
        Instant now = Instant.now();
        VmTemplateSchemaVersionIdDto vmTemplateSchemaVersionIdDto = vmTemplateSchemaVersionService.getVmTemplateSchemaVersionById(dto.versionId());
        VmTemplateSchemaVersionEntity versionEntity = vmTemplateSchemaVersionMapper.toEntity(vmTemplateSchemaVersionIdDto);

        VmTemplateSchemaEntity basicSchemaEntity = saveSchemaEntity(
                dto.basicSchema(),
                versionEntity,
                VmTemplateSchemaType.BASIC,
                now
        );

        VmTemplateSchemaEntity nestedSchemaEntity = saveSchemaEntity(
                dto.nestedSchema(),
                versionEntity,
                VmTemplateSchemaType.NESTED,
                now
        );

        return List.of(
                toDto(basicSchemaEntity),
                toDto(nestedSchemaEntity)
        );
    }

    private VmTemplateSchemaEntity saveSchemaEntity(MultipartFile file,
                                                    VmTemplateSchemaVersionEntity version,
                                                    VmTemplateSchemaType schemaType,
                                                    Instant timestamp) throws IOException {

        VmTemplateSchemaTypeIdDto vmTemplateSchemaTypeIdDto = vmTemplateSchemaTypeService.getVmTemplateSchemaTypeByName(schemaType.name().toLowerCase());
        VmTemplateSchemaTypeEntity typeEntity = vmTemplateSchemaTypeMapper.toEntity(vmTemplateSchemaTypeIdDto);

        VmTemplateSchemaEntity entity = new VmTemplateSchemaEntity();
        entity.setName(file.getOriginalFilename());
        entity.setVersion(version);
        entity.setSchemaType(typeEntity);
        entity.setFileContent(file.getBytes());
        entity.setUploadedAt(timestamp);
        entity.setModifiedAt(timestamp);

        return vmTemplateSchemaRepository.save(entity);
    }

    @Override
    @Transactional
    public VmTemplateSchemaDto updateVmTemplateSchema(Integer id, VmTemplateSchemaUpdateDto dto) throws IOException {
        VmTemplateSchemaEntity entity = vmTemplateSchemaRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateSchemaNotFoundException(id));

        VmTemplateSchemaVersionIdDto vmTemplateSchemaVersionIdDto = vmTemplateSchemaVersionService.getVmTemplateSchemaVersionById(dto.versionId());
        VmTemplateSchemaVersionEntity vmTemplateSchemaVersionEntity = vmTemplateSchemaVersionMapper.toEntity(vmTemplateSchemaVersionIdDto);

        entity.setName(dto.file().getOriginalFilename());
        entity.setVersion(vmTemplateSchemaVersionEntity);
        entity.setFileContent(dto.file().getBytes());
        entity.setModifiedAt(Instant.now());
        VmTemplateSchemaEntity updatedEntity = vmTemplateSchemaRepository.save(entity);
        return toDto(updatedEntity);
    }

    @Override
    public List<VmTemplateSchemaDto> getVmTemplateSchemas() {
        return vmTemplateSchemaRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VmTemplateSchemesGetDto getVmTemplateSchemesByVersionId(Integer versionId) {
        List<VmTemplateSchemaEntity> vmTemplateSchemaEntities = vmTemplateSchemaRepository
                .findAllWithVersionByVersionId(versionId)
                .stream()
                .toList();

        //todo: не безопасный вариант, если в бд изменится name
        String basicTypeName = VmTemplateSchemaType.BASIC.name().toLowerCase();
        String nestedTypeName = VmTemplateSchemaType.NESTED.name().toLowerCase();

        VmTemplateSchemaEntity basicSchema = vmTemplateSchemaEntities.stream()
                .filter(entity -> entity.getSchemaType().getName().equals(basicTypeName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("BASIC-схема не найдена"));

        VmTemplateSchemaEntity nestedSchema = vmTemplateSchemaEntities.stream()
                .filter(entity -> entity.getSchemaType().getName().equals(nestedTypeName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("NESTED-схема не найдена"));

        VmTemplateSchemaFileDto basicSchemaFile = new VmTemplateSchemaFileDto(basicSchema.getName(), basicSchema.getFileContent());
        VmTemplateSchemaFileDto nestedSchemaFile = new VmTemplateSchemaFileDto(nestedSchema.getName(), nestedSchema.getFileContent());
        return new VmTemplateSchemesGetDto(
                basicSchemaFile,
                nestedSchemaFile
        );
    }

    @Override
    public VmTemplateSchemaDto getVmTemplateSchemaById(Integer id) {
        VmTemplateSchemaEntity entity = vmTemplateSchemaRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateSchemaNotFoundException(id));
        return toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public VmTemplateSchemaFileDto getVmTemplateFileByVersionAndTypeId(Integer versionId, Integer typeId) {
        VmTemplateSchemaEntity vmTemplateSchema = vmTemplateSchemaRepository
                .findByVersionIdAndSchemaTypeId(versionId, typeId)
                .orElseThrow(() -> new VmTemplateSchemaNotFoundException(versionId));

        return new VmTemplateSchemaFileDto(vmTemplateSchema.getName(), vmTemplateSchema.getFileContent());
    }

    private VmTemplateSchemaDto toDto(VmTemplateSchemaEntity entity) {
        return new VmTemplateSchemaDto(
                entity.getId(),
                entity.getName(),
                entity.getVersion().getName(),
                entity.getSchemaType().getName(),
                entity.getUploadedAt(),
                entity.getModifiedAt()
        );
    }

    @Override
    @Transactional
    public void deleteVmTemplateSchema(Integer id) {
        VmTemplateSchemaEntity entity = vmTemplateSchemaRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateSchemaNotFoundException(id));
        vmTemplateSchemaRepository.delete(entity);
    }
}