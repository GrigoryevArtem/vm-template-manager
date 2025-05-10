package company.vm.service;


import company.vm.dto.service.spr.VmGlobalVariableDto;
import company.vm.dto.service.spr.VmGlobalVariableIdDto;

import java.util.List;


public interface VmGlobalVariableService {

    VmGlobalVariableIdDto createGlobalVariable(VmGlobalVariableDto dto);

    VmGlobalVariableIdDto getGlobalVariableById(Integer id);

    VmGlobalVariableIdDto updateGlobalVariable(Integer id, VmGlobalVariableDto dto);

    List<VmGlobalVariableIdDto> getGlobalVariables();

    void deleteGlobalVariable(Integer id);

}
