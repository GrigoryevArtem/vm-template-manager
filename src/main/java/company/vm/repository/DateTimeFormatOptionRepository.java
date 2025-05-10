package company.vm.repository;


import company.vm.entity.DateTimeFormatOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DateTimeFormatOptionRepository extends JpaRepository<DateTimeFormatOptionEntity, Integer> {
}
