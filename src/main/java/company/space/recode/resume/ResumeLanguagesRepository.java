package company.space.recode.resume;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeLanguagesRepository extends JpaRepository<Languages, Long> {
    List<Languages> findByRegiId(String regiId, Sort sort);
}
