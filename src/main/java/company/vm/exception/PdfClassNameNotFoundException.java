package company.vm.exception;


public class PdfClassNameNotFoundException extends RuntimeException {
    public PdfClassNameNotFoundException(String message) {
        super(message);
    }

    public PdfClassNameNotFoundException(Integer id) {
        super("ClassName pdf c id " + id + " не найден");
    }
}
