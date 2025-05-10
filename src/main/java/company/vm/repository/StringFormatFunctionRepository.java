package company.vm.repository;


import company.vm.entity.StringFormatFunctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StringFormatFunctionRepository extends JpaRepository<StringFormatFunctionEntity, Integer> {
}
