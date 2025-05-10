package company.vm.service.template;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import company.vm.dto.service.pdf.PdfComponentDto;
import company.vm.dto.service.template.*;
import company.vm.dto.service.xml.RegionServiceInfo;
import company.vm.entity.VmTemplateEntity;
import company.vm.entity.VmTemplateSchemaVersionEntity;
import company.vm.entity.VmTemplateTypeEntity;
import company.vm.exception.VmTemplateNotFoundException;
import company.vm.mapper.VmTemplateSchemaVersionMapper;
import company.vm.mapper.VmTemplateTypeMapper;
import company.vm.repository.VmTemplateRepository;
import company.vm.service.VmTemplateSchemaVersionService;
import company.vm.service.VmTemplateService;
import company.vm.service.VmTemplateTypeService;
import company.vm.service.generator.VmFileNameGenerator;
import company.vm.service.generator.pdf.TemplateBuilder;
import company.vm.service.generator.xml.VmHeaderGenerator;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;


@Service
public class VmTemplateServiceImpl implements VmTemplateService {
    private final VmHeaderGenerator vmHeaderGenerator;
    private final ObjectMapper objectMapper;

    private final VmTemplateRepository vmTemplateRepository;

    private final VmTemplateSchemaVersionService vmTemplateSchemaVersionService;
    private final VmTemplateSchemaVersionMapper vmTemplateSchemaVersionMapper;

    private final VmTemplateTypeService vmTemplateTypeService;
    private final VmTemplateTypeMapper vmTemplateTypeMapper;

    public VmTemplateServiceImpl(VmHeaderGenerator vmHeaderGenerator,
                                 ObjectMapper objectMapper,
                                 VmTemplateRepository vmTemplateRepository,
                                 VmTemplateSchemaVersionService vmTemplateSchemaVersionService,
                                 VmTemplateSchemaVersionMapper vmTemplateSchemaVersionMapper,
                                 VmTemplateTypeService vmTemplateTypeService,
                                 VmTemplateTypeMapper vmTemplateTypeMapper
    ) {
        this.vmHeaderGenerator = vmHeaderGenerator;
        this.objectMapper = objectMapper;
        this.vmTemplateRepository = vmTemplateRepository;
        this.vmTemplateSchemaVersionService = vmTemplateSchemaVersionService;
        this.vmTemplateSchemaVersionMapper = vmTemplateSchemaVersionMapper;
        this.vmTemplateTypeService = vmTemplateTypeService;
        this.vmTemplateTypeMapper = vmTemplateTypeMapper;
    }

    @Override
    public VmTemplateFileDto generateXmlTemplate(MultipartFile file) throws IOException {
        JsonNode json = objectMapper.readTree(file.getInputStream());
        JsonNode node = json.get("additionalInfo");
        JsonNode applicationInfo = json.get("applicationInfo");
        RegionServiceInfo regionServiceInfo = objectMapper.treeToValue(node, RegionServiceInfo.class);

        StringBuilder builder = vmHeaderGenerator.execute(regionServiceInfo, applicationInfo);

        String fileName = VmFileNameGenerator.generateXmlTemplateFileName(regionServiceInfo.serviceCode());
        byte[] fileBytes = builder.toString().getBytes(StandardCharsets.UTF_8);
        return new VmTemplateFileDto(
                fileName,
                MediaType.APPLICATION_JSON_VALUE,
                fileBytes
        );
    }

    @Override
    public VmTemplateFileDto generatePdfTemplate(String serviceNumber, MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<PdfComponentDto> components = mapper.readValue(file.getInputStream(), new TypeReference<>() {
        });
        String pdfTemplateContent = new TemplateBuilder()
                .processComponents(components, 1, null)
                .build();

        String fileName = VmFileNameGenerator.generatePdfTemplateFileName(serviceNumber);
        byte[] fileBytes = pdfTemplateContent.getBytes(StandardCharsets.UTF_8);

        return new VmTemplateFileDto(
                fileName,
                MediaType.APPLICATION_JSON_VALUE,
                fileBytes
        );
    }

    @Override
    @Transactional
    public VmTemplateDto saveTemplate(VmTemplateServiceDto dto) throws IOException {
        VmTemplateSchemaVersionIdDto versionIdDto = vmTemplateSchemaVersionService.getVmTemplateSchemaVersionById(dto.versionId());
        VmTemplateSchemaVersionEntity versionEntity = vmTemplateSchemaVersionMapper.toEntity(versionIdDto);
        VmTemplateTypeIdDto vmTemplateTypeIdDto = vmTemplateTypeService.getVmTemplateTypeById(dto.type());
        VmTemplateTypeEntity vmTemplateTypeEntity = vmTemplateTypeMapper.toEntity(vmTemplateTypeIdDto);

        VmTemplateEntity entity = getVmTemplateEntity(dto, versionEntity, vmTemplateTypeEntity);
        VmTemplateEntity savedEntity = vmTemplateRepository.save(entity);
        return getVmTemplateDto(savedEntity);
    }

    @NotNull
    private static VmTemplateEntity getVmTemplateEntity(VmTemplateServiceDto dto,
                                                        VmTemplateSchemaVersionEntity versionEntity,
                                                        VmTemplateTypeEntity typeEntity) throws IOException {
        VmTemplateEntity entity = new VmTemplateEntity();
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

    @NotNull
    private VmTemplateDto getVmTemplateDto(VmTemplateEntity savedEntity) {
        return new VmTemplateDto(
                savedEntity.getId(),
                savedEntity.getName(),
                savedEntity.getDescription(),
                savedEntity.getVersion().getName(),
                savedEntity.getType().getName(),
                savedEntity.getModifiedAt(),
                savedEntity.getModifiedAt()
        );
    }

    @Override
    public VmTemplateFileDto downloadTemplateById(Integer id) {
        VmTemplateEntity template = vmTemplateRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateNotFoundException(id));

        return new VmTemplateFileDto(template.getName(), template.getFileFormat(), template.getFileContent());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VmTemplateDto> getPreviewTemplates(Pageable pageRequest) {
        Page<VmTemplateEntity> templates = vmTemplateRepository.findAll(pageRequest);
        List<VmTemplateDto> dtos = templates.stream()
                .map(this::getVmTemplateDto)
                .toList();
        return new PageImpl<>(dtos, templates.getPageable(), templates.getTotalElements());
    }

    @Override
    @Transactional
    public VmTemplateDto updateTemplate(Integer id, VmTemplateServiceDto dto) throws IOException {
        VmTemplateEntity template = vmTemplateRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateNotFoundException(id));

        VmTemplateSchemaVersionIdDto versionIdDto = vmTemplateSchemaVersionService.getVmTemplateSchemaVersionById(dto.versionId());
        VmTemplateSchemaVersionEntity versionEntity = vmTemplateSchemaVersionMapper.toEntity(versionIdDto);
        template.setName(dto.name());
        template.setDescription(dto.description());
        template.setVersion(versionEntity);
        template.setFileContent(dto.fileContent().getBytes());
        template.setFileFormat(dto.fileContent().getContentType());
        template.setModifiedAt(Instant.now());

        VmTemplateEntity savedEntity = vmTemplateRepository.save(template);
        return getVmTemplateDto(savedEntity);
    }

    @Override
    @Transactional
    public void deleteVmTemplate(Integer id) {
        VmTemplateEntity template = vmTemplateRepository
                .findById(id)
                .orElseThrow(() -> new VmTemplateNotFoundException(id));

        vmTemplateRepository.delete(template);
    }

}
