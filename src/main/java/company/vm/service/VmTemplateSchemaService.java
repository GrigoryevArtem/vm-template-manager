package company.vm.service;


import company.vm.dto.service.template.*;

import java.io.IOException;
import java.util.List;


public interface VmTemplateSchemaService {

    List<VmTemplateSchemaDto> createVmTemplateSchemas(VmTemplateSchemesCreateDto dto) throws IOException;

    VmTemplateSchemaDto updateVmTemplateSchema(Integer id, VmTemplateSchemaUpdateDto dto) throws IOException;

    List<VmTemplateSchemaDto> getVmTemplateSchemas();

    VmTemplateSchemesGetDto getVmTemplateSchemesByVersionId(Integer versionId);

    VmTemplateSchemaDto getVmTemplateSchemaById(Integer id);

    VmTemplateSchemaFileDto getVmTemplateFileByVersionAndTypeId(Integer versionId, Integer typeId);

    void deleteVmTemplateSchema(Integer id);

}
