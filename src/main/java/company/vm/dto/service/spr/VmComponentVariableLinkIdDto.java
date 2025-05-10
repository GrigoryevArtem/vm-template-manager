package company.vm.dto.service.spr;


public record VmComponentVariableLinkIdDto(
        Integer id,
        Integer variableId,
        Integer componentId,
        Integer parentId
) {
}

