package company.vm.service;


import company.vm.dto.service.template.VmTemplateSchemaVersionDto;
import company.vm.dto.service.template.VmTemplateSchemaVersionIdDto;

import java.util.List;


public interface VmTemplateSchemaVersionService {

    VmTemplateSchemaVersionIdDto createVmTemplateSchemaVersion(VmTemplateSchemaVersionDto dto);

    VmTemplateSchemaVersionIdDto getVmTemplateSchemaVersionById(Integer id);

    VmTemplateSchemaVersionIdDto updateVmTemplateSchemaVersion(Integer id, VmTemplateSchemaVersionDto dto);

    List<VmTemplateSchemaVersionIdDto> getVmTemplateSchemaVersions();

    void deleteVmTemplateSchemaVersion(Integer id);

}
