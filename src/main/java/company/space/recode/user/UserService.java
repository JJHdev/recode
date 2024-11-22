package company.space.recode.user;

import company.space.recode.component.Utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User userRegi(UserSaveForm userSaveForm){
        //UserDTO to user
        User user = new User();
        user.setUserId(userSaveForm.getUserId());
        user.setUserName(userSaveForm.getUserName());
        user.setPassword(userSaveForm.getPassword());
        user.setEmail(userSaveForm.getFullEmail());
        user.setGender(userSaveForm.getGender());
        user.setStatus(userSaveForm.getStatus());

        return userRepository.save(user);
    }

    public User login(String userId, String password){
        User user = userRepository.findByUserId(userId).orElseGet(() -> {
            // 빈 User 객체 생성
            User emptyUser = new User();
            emptyUser.setUserId(null); // 필요하면 기본값 설정
            emptyUser.setUserName("Anonymous");
            return emptyUser;
        });

        if(passwordEncoder.matches(password, user.getPassword())){
            return user;
        } else {
            return new User();
        }
    }

    public User findUserOrReturnEmpty(String userId) {
        return userRepository.findByUserId(userId)
                .orElseGet(() -> {
                    // 빈 User 객체 생성
                    User emptyUser = new User();
                    emptyUser.setUserId(null); // 필요하면 기본값 설정
                    emptyUser.setUserName("Anonymous");
                    return emptyUser;
                });
    }

    public ServiceResult<String> checkUser(String userId){
        //UserDTO to user
        User user = new User();
        user.setUserId(userId);
        int reuslt =  userRepository.countByUserId(userId);
        if(reuslt >= 1){
            return ServiceResult.failure(userId);
        }else{
            return ServiceResult.success(userId);
        }
    }


}
