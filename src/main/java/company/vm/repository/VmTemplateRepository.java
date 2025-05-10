package company.vm.repository;


import company.vm.entity.VmTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VmTemplateRepository extends JpaRepository<VmTemplateEntity, Integer> {
}
