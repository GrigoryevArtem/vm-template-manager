package company.vm.entity;


import company.vm.entity.meta.VmDbMeta;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


/**
 * Тип вм-шаблона
 */
@Entity
@Table(name = VmTemplateTypeEntity.SQL_TABLE_NAME, schema = VmDbMeta.APP_SCHEMA_NAME)
public class VmTemplateTypeEntity extends BaseSprEntity {

    public static final String SQL_TABLE_NAME = "vm_template_type";
}
