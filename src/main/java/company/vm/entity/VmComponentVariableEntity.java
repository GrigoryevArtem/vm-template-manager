package company.vm.entity;


import company.vm.entity.meta.VmDbMeta;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


/**
 * Компонент vm-шаблона
 */
@Entity
@Table(name = VmComponentVariableEntity.SQL_TABLE_NAME, schema = VmDbMeta.APP_SCHEMA_NAME)
public class VmComponentVariableEntity extends BaseSprEntity {
    public static final String SQL_TABLE_NAME = "vm_component_variable";
}
