package in.sk.main.services;

import in.sk.main.entities.User;
import in.sk.main.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUserService(User user){
        userRepository.save(user);
    }

    public boolean loginUserService(String email,String password){
        User user= userRepository.findByEmail(email);
        if(user!=null){
            return password.equals(user.getPassword());
        }return false;
    }

    public Page<User> getAllUserService(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public User getUserByEmailService(String email){
        return userRepository.findByEmail(email);
    }

    public void updateUserBanStatus(User user){
        userRepository.save(user);
    }
}
