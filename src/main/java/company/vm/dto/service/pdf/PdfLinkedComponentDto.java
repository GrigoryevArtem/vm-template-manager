package company.vm.dto.service.pdf;


import java.util.List;


public record PdfLinkedComponentDto(
        String id,
        String type,
        String value,
        String answer,
        String answerText,
        List<String> postfixes,
        String dateFormat,
        String blockType,
        String operator

) {
}
