package company.vm.repository;


import company.vm.entity.VmComponentVariableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VmComponentVariableRepository extends JpaRepository<VmComponentVariableEntity, Integer> {
}
