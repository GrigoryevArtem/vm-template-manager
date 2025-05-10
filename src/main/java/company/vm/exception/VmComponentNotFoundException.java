package company.vm.exception;


public class VmComponentNotFoundException extends RuntimeException {
    public VmComponentNotFoundException(String message) {
        super(message);
    }

    public VmComponentNotFoundException(Integer id) {
        super("Vm-компонент с id " + id + " не найден");
    }
}
