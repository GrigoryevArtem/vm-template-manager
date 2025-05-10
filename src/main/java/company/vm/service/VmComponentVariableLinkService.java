package company.vm.service;


import company.vm.dto.controller.VmComponentVariableLinkNodeResponseDto;
import company.vm.dto.service.spr.VmComponentVariableLinkDto;
import company.vm.dto.service.spr.VmComponentVariableLinkIdDto;

import java.util.List;
import java.util.Map;


public interface VmComponentVariableLinkService {

    VmComponentVariableLinkIdDto createVmComponentVariableLink(VmComponentVariableLinkDto dto);

    VmComponentVariableLinkIdDto getVmComponentVariableLinkById(Integer id);

    VmComponentVariableLinkIdDto updateVmComponentLinkVariable(Integer id, VmComponentVariableLinkDto dto);

    List<VmComponentVariableLinkIdDto> getVmComponentLinkVariables();

    Map<String, List<VmComponentVariableLinkNodeResponseDto>> getGroupedVariablesLinkByComponentName();

    void deleteVmComponentVariableLink(Integer id);

}
