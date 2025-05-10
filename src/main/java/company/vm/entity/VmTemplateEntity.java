package company.vm.entity;


import company.vm.entity.meta.VmDbMeta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


/**
 * Vm-шаблон
 */
@Entity
@Table(name = VmTemplateEntity.SQL_TABLE_NAME, schema = VmDbMeta.APP_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VmTemplateEntity {
    public static final String SQL_TABLE_NAME = "vm_template";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private VmTemplateSchemaVersionEntity version;

    @ManyToOne(fetch = FetchType.LAZY)
    private VmTemplateTypeEntity type;

    @Lob
    @Column(nullable = false)
    private byte[] fileContent;

    private String fileFormat;

    private Instant uploadedAt;

    private Instant modifiedAt;
}
