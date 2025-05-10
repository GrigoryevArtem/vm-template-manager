package company.vm.exception;


public class VmTemplateSchemaVersionNotFoundException extends RuntimeException {
    public VmTemplateSchemaVersionNotFoundException(String message) {
        super(message);
    }

    public VmTemplateSchemaVersionNotFoundException(Integer id) {
        super(String.format("Версия схемы шаблона с id '%d' не найдена", id));
    }
}
