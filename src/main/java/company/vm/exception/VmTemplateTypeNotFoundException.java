package company.vm.exception;


public class VmTemplateTypeNotFoundException extends RuntimeException {
    public VmTemplateTypeNotFoundException(String message) {
        super(message);
    }

    public VmTemplateTypeNotFoundException(Integer id) {
        super(String.format("Тип шаблона с id '%d' не найден", id));
    }
}
