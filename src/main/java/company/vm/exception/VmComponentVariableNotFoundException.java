package company.vm.exception;


public class VmComponentVariableNotFoundException extends RuntimeException {
    public VmComponentVariableNotFoundException(String message) {
        super(message);
    }

    public VmComponentVariableNotFoundException(Integer id) {
        super("Переменная vm-компонента с id " + id + " не найдена");
    }
}
