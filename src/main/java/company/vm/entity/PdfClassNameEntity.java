package company.vm.entity;


import company.vm.entity.meta.VmDbMeta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Инструкции для pdf (className)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = PdfClassNameEntity.SQL_TABLE_NAME, schema = VmDbMeta.APP_SCHEMA_NAME)
public class PdfClassNameEntity extends BaseSprEntity {

    public static final String SQL_TABLE_NAME = "pdf_class_name";

    @Column(nullable = false)
    private Boolean hasValue;

}
