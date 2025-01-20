package company.space.recode.component.sequence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public String getNextSequenceValue(String sequenceName) {
        return entityManager.createNativeQuery("SELECT " + sequenceName + ".NEXTVAL FROM DUAL")
                .getSingleResult()
                .toString();
    }
}
