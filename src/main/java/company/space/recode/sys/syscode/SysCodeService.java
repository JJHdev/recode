package company.space.recode.sys.syscode;

import company.space.recode.component.Utils.ServiceResult;
import company.space.recode.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SysCodeService {

    private final SysCodeRepository sysCodeRepository;
    @Autowired
    public SysCodeService(SysCodeRepository sysCodeRepository){
        this.sysCodeRepository = sysCodeRepository;
    }

    public List<SysCode> findSysCode(String parentCode){
        return sysCodeRepository.findByParentCode(parentCode);
    }
}
