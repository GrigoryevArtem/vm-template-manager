package company.vm.service;


import company.vm.dto.service.template.VmTemplateTypeDto;
import company.vm.dto.service.template.VmTemplateTypeIdDto;

import java.util.List;


public interface VmTemplateTypeService {

    VmTemplateTypeIdDto createVmTemplateType(VmTemplateTypeDto dto);

    VmTemplateTypeIdDto getVmTemplateTypeById(Integer id);

    VmTemplateTypeIdDto updateVmTemplateType(Integer id, VmTemplateTypeDto dto);

    List<VmTemplateTypeIdDto> getVmTemplateTypes();

    void deleteVmTemplateType(Integer id);

}
