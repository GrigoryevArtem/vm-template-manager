package company.vm.repository;


import company.vm.entity.VmComponentVariableLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VmComponentVariableLinkRepository extends JpaRepository<VmComponentVariableLinkEntity, Integer> {
}
