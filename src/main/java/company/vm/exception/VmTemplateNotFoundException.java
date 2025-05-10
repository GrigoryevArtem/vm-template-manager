package company.vm.exception;


public class VmTemplateNotFoundException extends RuntimeException {
    public VmTemplateNotFoundException(String message) {
        super(message);
    }

    public VmTemplateNotFoundException(Integer id) {
        super(String.format("Vm-шаблон с id '%d' не найден", id));
    }
}
