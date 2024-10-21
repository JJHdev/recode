package company.space.recode.user;

import company.space.recode.component.Utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
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
