package company.vm.service.spr;


import company.vm.dto.controller.VmComponentVariableLinkNodeResponseDto;
import company.vm.dto.service.spr.VmComponentIdDto;
import company.vm.dto.service.spr.VmComponentVariableIdDto;
import company.vm.dto.service.spr.VmComponentVariableLinkDto;
import company.vm.dto.service.spr.VmComponentVariableLinkIdDto;
import company.vm.entity.VmComponentEntity;
import company.vm.entity.VmComponentVariableEntity;
import company.vm.entity.VmComponentVariableLinkEntity;
import company.vm.exception.VmComponentVariableLinkNotFoundException;
import company.vm.mapper.VmComponentMapper;
import company.vm.mapper.VmComponentVariableLinkMapper;
import company.vm.mapper.VmComponentVariableMapper;
import company.vm.repository.VmComponentVariableLinkRepository;
import company.vm.service.VmComponentService;
import company.vm.service.VmComponentVariableLinkService;
import company.vm.service.VmComponentVariableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class VmComponentVariableLinkServiceImpl implements VmComponentVariableLinkService {

    private final VmComponentVariableLinkRepository vmComponentVariableLinkRepository;
    private final VmComponentVariableLinkMapper vmComponentVariableLinkMapper;
    private final VmComponentService vmComponentService;
    private final VmComponentVariableService vmComponentVariableService;
    private final VmComponentMapper vmComponentMapper;
    private final VmComponentVariableMapper vmComponentVariableMapper;

    public VmComponentVariableLinkServiceImpl(VmComponentVariableLinkRepository vmComponentVariableLinkRepository,
                                              VmComponentVariableLinkMapper vmComponentVariableLinkMapper,
                                              VmComponentService vmComponentService,
                                              VmComponentVariableService vmComponentVariableService,
                                              VmComponentMapper vmComponentMapper,
                                              VmComponentVariableMapper vmComponentVariableMapper) {
        this.vmComponentVariableLinkRepository = vmComponentVariableLinkRepository;
        this.vmComponentVariableLinkMapper = vmComponentVariableLinkMapper;
        this.vmComponentService = vmComponentService;
        this.vmComponentVariableService = vmComponentVariableService;
        this.vmComponentMapper = vmComponentMapper;
        this.vmComponentVariableMapper = vmComponentVariableMapper;
    }

    @Override
    @Transactional
    public VmComponentVariableLinkIdDto createVmComponentVariableLink(VmComponentVariableLinkDto dto) {
        VmComponentVariableLinkEntity entity = new VmComponentVariableLinkEntity();
        dtoToEntity(dto, entity);
        VmComponentVariableLinkEntity createdEntity = vmComponentVariableLinkRepository.save(entity);
        return toDto(createdEntity);
    }

    @Override
    public VmComponentVariableLinkIdDto getVmComponentVariableLinkById(Integer id) {
        VmComponentVariableLinkEntity vmGlobalVariableEntity = vmComponentVariableLinkRepository
                .findById(id)
                .orElseThrow(() -> new VmComponentVariableLinkNotFoundException(id));
        return toDto(vmGlobalVariableEntity);
    }

    private VmComponentVariableLinkIdDto toDto(VmComponentVariableLinkEntity entity) {
        Integer variableId = Optional.ofNullable(entity.getVariable())
                .map(VmComponentVariableEntity::getId)
                .orElse(null);

        Integer componentId = Optional.ofNullable(entity.getComponent())
                .map(VmComponentEntity::getId)
                .orElse(null);

        Integer parentId = Optional.ofNullable(entity.getParent())
                .map(VmComponentVariableLinkEntity::getId)
                .orElse(null);

        return new VmComponentVariableLinkIdDto(
                entity.getId(),
                variableId,
                componentId,
                parentId
        );
    }

    private VmComponentVariableLinkEntity toEntity(VmComponentVariableLinkIdDto dto) {
        VmComponentVariableIdDto vmComponentVariableDto = vmComponentVariableService.getVmComponentVariableById(dto.variableId());
        VmComponentVariableEntity vmComponentVariableEntity = vmComponentVariableMapper.toEntity(vmComponentVariableDto);

        VmComponentVariableLinkIdDto vmComponentVariableLinkIdDto = getVmComponentVariableLinkById(dto.parentId());
        VmComponentVariableLinkEntity vmComponentVariableLinkEntity = new VmComponentVariableLinkEntity();
        VmComponentVariableLinkDto vmComponentVariableLinkDto = vmComponentVariableLinkMapper.toServiceDto(vmComponentVariableLinkIdDto);
        dtoToEntity(vmComponentVariableLinkDto, vmComponentVariableLinkEntity);

        VmComponentIdDto vmComponentDto = vmComponentService.getVmComponentById(dto.variableId());
        VmComponentEntity vmComponentEntity = vmComponentMapper.toEntity(vmComponentDto);

        return new VmComponentVariableLinkEntity(
                dto.id(),
                vmComponentEntity,
                vmComponentVariableEntity,
                vmComponentVariableLinkEntity);
    }

    @Override
    @Transactional
    public VmComponentVariableLinkIdDto updateVmComponentLinkVariable(Integer id, VmComponentVariableLinkDto dto) {
        VmComponentVariableLinkEntity entity = vmComponentVariableLinkRepository
                .findById(id)
                .orElseThrow(() -> new VmComponentVariableLinkNotFoundException(id));

        dtoToEntity(dto, entity);
        VmComponentVariableLinkEntity updatedEntity = vmComponentVariableLinkRepository.save(entity);
        return toDto(updatedEntity);
    }

    private void dtoToEntity(VmComponentVariableLinkDto dto, VmComponentVariableLinkEntity entity) {
        VmComponentIdDto vmComponentDto = vmComponentService.getVmComponentById(dto.parentId());
        VmComponentEntity vmComponentEntity = vmComponentMapper.toEntity(vmComponentDto);

        VmComponentVariableLinkIdDto vmComponentVariableLinkIdDto = getVmComponentVariableLinkById(dto.parentId());
        VmComponentVariableLinkEntity vmComponentVariableLinkEntity = toEntity(vmComponentVariableLinkIdDto);

        VmComponentVariableIdDto vmComponentVariableDto = vmComponentVariableService.getVmComponentVariableById(dto.variableId());
        VmComponentVariableEntity vmComponentVariableEntity = vmComponentVariableMapper.toEntity(vmComponentVariableDto);

        entity.setVariable(vmComponentVariableEntity);
        entity.setComponent(vmComponentEntity);
        entity.setParent(vmComponentVariableLinkEntity);
    }

    @Override
    public List<VmComponentVariableLinkIdDto> getVmComponentLinkVariables() {
        return vmComponentVariableLinkRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Map<String, List<VmComponentVariableLinkNodeResponseDto>> getGroupedVariablesLinkByComponentName() {
        List<VmComponentVariableLinkEntity> allVariables = vmComponentVariableLinkRepository.findAll();
        Map<String, List<VmComponentVariableLinkEntity>> groupedByComponent = allVariables.stream()
                .collect(Collectors.groupingBy(v -> v.getComponent().getName()));

        Map<String, List<VmComponentVariableLinkNodeResponseDto>> result = new HashMap<>();

        for (Map.Entry<String, List<VmComponentVariableLinkEntity>> entry : groupedByComponent.entrySet()) {
            String componentName = entry.getKey();
            List<VmComponentVariableLinkEntity> variables = entry.getValue();

            List<VmComponentVariableLinkNodeResponseDto> rootNodes = variables.stream()
                    .filter(v -> v.getParent() == null)
                    .map(this::buildTree)
                    .toList();

            result.put(componentName, rootNodes);
        }

        return result;
    }

    private VmComponentVariableLinkNodeResponseDto buildTree(VmComponentVariableLinkEntity entity) {
        List<VmComponentVariableLinkNodeResponseDto> childNodes = entity.getChildren().stream()
                .map(this::buildTree)
                .toList();

        VmComponentVariableIdDto vmComponentVariable = vmComponentVariableService.getVmComponentVariableById(entity.getVariable().getId());
        return new VmComponentVariableLinkNodeResponseDto(
                vmComponentVariable.name(),
                vmComponentVariable.description(),
                childNodes
        );
    }

    @Override
    @Transactional
    public void deleteVmComponentVariableLink(Integer id) {
        VmComponentVariableLinkEntity vmGlobalVariableEntity = vmComponentVariableLinkRepository
                .findById(id)
                .orElseThrow(() -> new VmComponentVariableLinkNotFoundException(id));
        vmComponentVariableLinkRepository.delete(vmGlobalVariableEntity);
    }
}
