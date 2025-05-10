package company.vm.entity;


import company.vm.entity.meta.VmDbMeta;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


/**
 * Глобальные переменные
 */
@Entity
@Table(name = VmGlobalVariableEntity.SQL_TABLE_NAME, schema = VmDbMeta.APP_SCHEMA_NAME)
public class VmGlobalVariableEntity extends BaseSprEntity {

    public static final String SQL_TABLE_NAME = "vm_global_variable";
}
