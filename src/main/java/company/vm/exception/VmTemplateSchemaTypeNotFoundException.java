package company.vm.exception;


public class VmTemplateSchemaTypeNotFoundException extends RuntimeException {
    public VmTemplateSchemaTypeNotFoundException(String message) {
        super(message);
    }

    public VmTemplateSchemaTypeNotFoundException(Integer id) {
        super("Тип схемы vm-шаблона с " + id + " не найден");
    }

}
