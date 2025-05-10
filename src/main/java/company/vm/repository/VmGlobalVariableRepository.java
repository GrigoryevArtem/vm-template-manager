package company.vm.repository;


import company.vm.entity.VmGlobalVariableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VmGlobalVariableRepository extends JpaRepository<VmGlobalVariableEntity, Integer> {
}
