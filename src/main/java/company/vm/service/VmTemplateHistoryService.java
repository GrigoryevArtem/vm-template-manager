package company.vm.service;


import company.vm.dto.service.template.VmTemplateHistoryDto;
import company.vm.dto.service.template.VmTemplateHistoryFileDto;
import company.vm.dto.service.template.VmTemplateHistoryServiceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;


public interface VmTemplateHistoryService {

    VmTemplateHistoryDto saveVmTemplateHistory(VmTemplateHistoryServiceDto dto) throws IOException;

    VmTemplateHistoryFileDto downloadVmTemplateHistoryById(Integer id);

    Page<VmTemplateHistoryDto> getPreviewVmTemplateHistories(Pageable pageRequest);

    VmTemplateHistoryDto updateVmTemplateHistory(Integer id, VmTemplateHistoryServiceDto dto) throws IOException;

    void deleteVmTemplateHistory(Integer id);

}
