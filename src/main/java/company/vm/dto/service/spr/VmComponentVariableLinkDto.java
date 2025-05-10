package company.vm.dto.service.spr;


public record VmComponentVariableLinkDto(
        Integer variableId,
        Integer componentId,
        Integer parentId
) {
}
