package company.space.recode.sys.syscode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysCodeRepository extends JpaRepository<SysCode,String> {
    List<SysCode> findByParentCode(String sysCode);
}
