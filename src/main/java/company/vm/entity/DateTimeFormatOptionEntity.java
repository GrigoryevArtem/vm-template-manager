package company.vm.entity;


import company.vm.entity.meta.VmDbMeta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Форматы даты и времени
 */
@Entity
@Table(name = DateTimeFormatOptionEntity.SQL_TABLE_NAME, schema = VmDbMeta.APP_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateTimeFormatOptionEntity {

    public static final String SQL_TABLE_NAME = "date_time_format_option";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(nullable = false)
    private String formatPattern;
}
