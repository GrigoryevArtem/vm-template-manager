package company.vm.exception;


public class VmGlobalVariableNotFoundException extends RuntimeException {
    public VmGlobalVariableNotFoundException(String message) {
        super(message);
    }

    public VmGlobalVariableNotFoundException(Integer id) {
        super("Глобальная перменнеая с id " + id + " не найдена");
    }
}
