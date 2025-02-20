package company.space.recode.file;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByExternalSeq(String externalSeq , Sort sort);
    File findFileByseqCode(Long seqCode , Sort sort);
    void deleteById(Long seqCode);
}
