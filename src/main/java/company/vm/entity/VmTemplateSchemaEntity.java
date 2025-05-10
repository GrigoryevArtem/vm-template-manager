package company.vm.entity;


import company.vm.entity.meta.VmDbMeta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


/**
 * Схема vm-шаблона
 */
@Entity
@Table(name = VmTemplateSchemaEntity.SQL_TABLE_NAME, schema = VmDbMeta.APP_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VmTemplateSchemaEntity {
    public static final String SQL_TABLE_NAME = "vm_template_schema";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private VmTemplateSchemaVersionEntity version;

    @ManyToOne(fetch = FetchType.LAZY)
    private VmTemplateSchemaTypeEntity schemaType;

    @Lob
    @Column(nullable = false)
    private byte[] fileContent;

    private Instant uploadedAt;

    private Instant modifiedAt;
}
