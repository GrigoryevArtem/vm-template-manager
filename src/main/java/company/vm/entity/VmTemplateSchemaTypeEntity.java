package company.vm.entity;


import company.vm.entity.meta.VmDbMeta;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;


/**
 * Тип схемы вм-шаблона
 */
@Entity
@Table(
        name = VmTemplateSchemaTypeEntity.SQL_TABLE_NAME,
        schema = VmDbMeta.APP_SCHEMA_NAME,
        indexes = {
                @Index(name = "idx_vm_template_schema_type_name", columnList = "name")
        }
)
public class VmTemplateSchemaTypeEntity extends BaseSprEntity {

    public static final String SQL_TABLE_NAME = "vm_template_schema_type";

}
