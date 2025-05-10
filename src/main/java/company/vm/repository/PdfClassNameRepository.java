package company.vm.repository;


import company.vm.entity.PdfClassNameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PdfClassNameRepository extends JpaRepository<PdfClassNameEntity, Integer> {
}
