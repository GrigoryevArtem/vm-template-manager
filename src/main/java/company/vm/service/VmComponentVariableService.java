package company.vm.service;


import company.vm.dto.service.spr.VmComponentVariableDto;
import company.vm.dto.service.spr.VmComponentVariableIdDto;

import java.util.List;


public interface VmComponentVariableService {

    VmComponentVariableIdDto createVmComponentVariable(VmComponentVariableDto dto);

    VmComponentVariableIdDto getVmComponentVariableById(Integer id);

    VmComponentVariableIdDto updateVmComponentVariable(Integer id, VmComponentVariableDto dto);

    List<VmComponentVariableIdDto> getVmComponentVariables();

    void deleteVmComponentVariable(Integer id);

}
