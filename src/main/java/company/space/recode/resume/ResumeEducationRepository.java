package company.space.recode.resume;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeEducationRepository extends JpaRepository<Education, Long> {
    List<Education> findByRegiId(String regiId, Sort sort);
}
