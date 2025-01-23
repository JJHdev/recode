package company.space.recode.email;


import company.space.recode.contact.ContactEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactEmailRepository extends JpaRepository<ContactEmail, Long> {

}
