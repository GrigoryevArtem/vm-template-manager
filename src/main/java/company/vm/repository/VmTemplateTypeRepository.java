package company.vm.repository;


import company.vm.entity.VmTemplateTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VmTemplateTypeRepository extends JpaRepository<VmTemplateTypeEntity, Integer> {
}
