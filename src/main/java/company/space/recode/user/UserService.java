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

    public ServiceResult<String> checkUser(String userId){
        //UserDTO to user
        User user = new User();
        user.setUserId(userId);
        //유효성검사
        /*
        try {
            ValidUserIdDuplicate(users);
            ValidUserIdCheck(users);
            return ServiceResult.success(userId);
        } catch (ValidationUserException e){
            return ServiceResult.failure(e.getMessage());
        } catch (Exception e) {
            return ServiceResult.failure("An unexpected error occurred: " + e.getMessage());
        }
        */
        return ServiceResult.success(userId);
    }

}
