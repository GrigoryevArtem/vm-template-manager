package company.vm.entity;


import company.vm.entity.meta.VmDbMeta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Версия схемы вм-шаблона
 */
@Entity
@Table(name = VmTemplateSchemaVersionEntity.SQL_TABLE_NAME, schema = VmDbMeta.APP_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VmTemplateSchemaVersionEntity {

    public static final String SQL_TABLE_NAME = "vm_template_schema_version";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
