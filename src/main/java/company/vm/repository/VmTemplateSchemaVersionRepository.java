package company.vm.repository;


import company.vm.entity.VmTemplateSchemaVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VmTemplateSchemaVersionRepository extends JpaRepository<VmTemplateSchemaVersionEntity, Integer> {

}
