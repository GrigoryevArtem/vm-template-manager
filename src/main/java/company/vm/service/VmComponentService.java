package company.vm.service;


import company.vm.dto.service.spr.VmComponentDto;
import company.vm.dto.service.spr.VmComponentIdDto;

import java.util.List;


public interface VmComponentService {

    VmComponentIdDto createVmComponent(VmComponentDto dto);

    VmComponentIdDto getVmComponentById(Integer id);

    VmComponentIdDto updateVmComponent(Integer id, VmComponentDto dto);

    List<VmComponentIdDto> getVmComponents();

    void deleteVmComponent(Integer id);

}
