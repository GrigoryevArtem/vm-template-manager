package company.vm.repository;


import company.vm.entity.VmComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VmComponentRepository extends JpaRepository<VmComponentEntity, Integer> {
}
