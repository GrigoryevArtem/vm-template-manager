package company.vm.repository;


import company.vm.entity.VmTemplateSchemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VmTemplateSchemaRepository extends JpaRepository<VmTemplateSchemaEntity, Integer> {

    @Query("SELECT v FROM VmTemplateSchemaEntity v JOIN FETCH v.version WHERE v.version.id = :versionId")
    List<VmTemplateSchemaEntity> findAllWithVersionByVersionId(@Param("versionId") Integer versionId);

    Optional<VmTemplateSchemaEntity> findByVersionIdAndSchemaTypeId(Integer versionId, Integer schemaTypeId);
}
