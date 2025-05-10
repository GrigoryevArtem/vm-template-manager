package company.vm.repository;


import company.vm.entity.VmTemplateSchemaTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VmTemplateSchemaTypeRepository extends JpaRepository<VmTemplateSchemaTypeEntity, Integer> {
    Optional<VmTemplateSchemaTypeEntity> findByName(String name);
}
