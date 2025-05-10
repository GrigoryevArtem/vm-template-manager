package company.vm.repository;


import company.vm.entity.VmTemplateHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VmTemplateHistoryRepository extends JpaRepository<VmTemplateHistoryEntity, Integer> {
}
