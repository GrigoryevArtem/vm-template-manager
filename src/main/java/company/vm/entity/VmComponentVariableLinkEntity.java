package company.vm.entity;


import company.vm.entity.meta.VmDbMeta;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Переменные компонентов
 */
@Entity
@Table(name = VmComponentVariableLinkEntity.SQL_TABLE_NAME, schema = VmDbMeta.APP_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VmComponentVariableLinkEntity {

    public static final String SQL_TABLE_NAME = "vm_component_variable_link";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private VmComponentEntity component;

    @ManyToOne(fetch = FetchType.LAZY)
    private VmComponentVariableEntity variable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private VmComponentVariableLinkEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<VmComponentVariableLinkEntity> children = new ArrayList<>();

    public VmComponentVariableLinkEntity(Integer id,
                                         VmComponentEntity component,
                                         VmComponentVariableEntity variable,
                                         VmComponentVariableLinkEntity parent
    ) {
        this.id = id;
        this.component = component;
        this.variable = variable;
        this.parent = parent;
    }

}
