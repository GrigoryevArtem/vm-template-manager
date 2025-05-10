package company.vm.service;


import company.vm.dto.service.template.VmTemplateSchemaTypeDto;
import company.vm.dto.service.template.VmTemplateSchemaTypeIdDto;

import java.util.List;


public interface VmTemplateSchemaTypeService {

    VmTemplateSchemaTypeIdDto createVmTemplateSchemaType(VmTemplateSchemaTypeDto dto);

    VmTemplateSchemaTypeIdDto getVmTemplateSchemaTypeById(Integer id);

    VmTemplateSchemaTypeIdDto getVmTemplateSchemaTypeByName(String name);

    VmTemplateSchemaTypeIdDto updateVmTemplateSchemaType(Integer id, VmTemplateSchemaTypeDto dto);

    List<VmTemplateSchemaTypeIdDto> getVmTemplateSchemaTypes();

    void deleteVmTemplateSchemaType(Integer id);

}
