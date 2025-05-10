package company.vm.exception;


public class StringFormatFunctionNotFoundException extends RuntimeException {
    public StringFormatFunctionNotFoundException(String message) {
        super(message);
    }

    public StringFormatFunctionNotFoundException(Integer id) {
        super("Функция формата строки с id " + id + " не найдена");
    }
}
