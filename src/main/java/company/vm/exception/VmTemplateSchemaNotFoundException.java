package company.vm.exception;


public class VmTemplateSchemaNotFoundException extends RuntimeException {
    public VmTemplateSchemaNotFoundException(String message) {
        super(message);
    }

    public VmTemplateSchemaNotFoundException(Integer id) {
        super(String.format("Схема vm-шаблона с id %s не найдена", id));
    }
}
