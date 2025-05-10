package company.vm.entity;


import company.vm.entity.meta.VmDbMeta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Функции форматирования строк с завязкой на тег
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = StringFormatFunctionEntity.SQL_TABLE_NAME, schema = VmDbMeta.APP_SCHEMA_NAME)
public class StringFormatFunctionEntity {
    public static final String SQL_TABLE_NAME = "string_format_function";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String tagName;
    @Column(columnDefinition = "TEXT")
    private String function;
}
