package company.space.recode.resume;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByRegiId(String regiId);
}
