package company.vm.service.spr;


import company.vm.dto.service.spr.PdfClassNameDto;
import company.vm.dto.service.spr.PdfClassNameIdDto;
import company.vm.entity.PdfClassNameEntity;
import company.vm.exception.PdfClassNameNotFoundException;
import company.vm.mapper.PdfClassNameMapper;
import company.vm.repository.PdfClassNameRepository;
import company.vm.service.PdfClassNameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PdfClassNameServiceImpl implements PdfClassNameService {

    private final PdfClassNameRepository pdfClassNameRepository;
    private final PdfClassNameMapper pdfClassNameMapper;

    public PdfClassNameServiceImpl(PdfClassNameRepository pdfClassNameRepository,
                                   PdfClassNameMapper pdfClassNameMapper
    ) {
        this.pdfClassNameRepository = pdfClassNameRepository;
        this.pdfClassNameMapper = pdfClassNameMapper;
    }

    @Override
    @Transactional
    public PdfClassNameIdDto createPdfClassName(PdfClassNameDto dto) {
        PdfClassNameEntity newEntity = pdfClassNameMapper.toEntity(dto);
        PdfClassNameEntity savedEntity = pdfClassNameRepository.save(newEntity);
        return pdfClassNameMapper.toDto(savedEntity);
    }

    @Override
    public PdfClassNameIdDto getPdfClassNameById(Integer id) {
        PdfClassNameEntity entity = pdfClassNameRepository
                .findById(id)
                .orElseThrow(() -> new PdfClassNameNotFoundException(id));
        return pdfClassNameMapper.toDto(entity);
    }

    @Override
    @Transactional
    public PdfClassNameIdDto updatePdfClassName(Integer id, PdfClassNameDto dto) {
        PdfClassNameEntity entity = pdfClassNameRepository
                .findById(id)
                .orElseThrow(() -> new PdfClassNameNotFoundException(id));

        entity.setName(dto.name());
        entity.setDescription(dto.description());
        PdfClassNameEntity updatedEntity = pdfClassNameRepository.save(entity);
        return pdfClassNameMapper.toDto(updatedEntity);
    }

    @Override
    public List<PdfClassNameIdDto> getPdfClassNames() {
        return pdfClassNameRepository
                .findAll()
                .stream()
                .map(pdfClassNameMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deletePdfClassName(Integer id) {
        PdfClassNameEntity entity = pdfClassNameRepository
                .findById(id)
                .orElseThrow(() -> new PdfClassNameNotFoundException(id));

        pdfClassNameRepository.delete(entity);
    }
}
