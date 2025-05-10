package company.vm.exception;


public class DateTimeFormatOptionNotFoundException extends RuntimeException {
    public DateTimeFormatOptionNotFoundException(String message) {
        super(message);
    }

    public DateTimeFormatOptionNotFoundException(Integer id) {
        super("Формат даты с  id " + id + " не найден");
    }
}
