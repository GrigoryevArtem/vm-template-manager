package company.vm.service;


import company.vm.dto.service.spr.PdfClassNameDto;
import company.vm.dto.service.spr.PdfClassNameIdDto;

import java.util.List;


public interface PdfClassNameService {

    PdfClassNameIdDto createPdfClassName(PdfClassNameDto dto);

    PdfClassNameIdDto getPdfClassNameById(Integer id);

    PdfClassNameIdDto updatePdfClassName(Integer id, PdfClassNameDto dto);

    List<PdfClassNameIdDto> getPdfClassNames();

    void deletePdfClassName(Integer id);
}
