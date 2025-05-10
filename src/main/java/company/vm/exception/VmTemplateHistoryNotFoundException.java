package company.vm.exception;


public class VmTemplateHistoryNotFoundException extends RuntimeException {
    public VmTemplateHistoryNotFoundException(String message) {
        super(message);
    }

    public VmTemplateHistoryNotFoundException(Integer id) {
        super(String.format("История сборки шаблона с id %s не найдена", id));
    }
}
