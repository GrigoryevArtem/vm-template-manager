package company.vm.exception;


public class VmComponentVariableLinkNotFoundException extends RuntimeException {
    public VmComponentVariableLinkNotFoundException(String message) {
        super(message);
    }

    public VmComponentVariableLinkNotFoundException(Integer id) {
        super("Связь переменной vm-компонента c id " + id + " не найдена");
    }
}
