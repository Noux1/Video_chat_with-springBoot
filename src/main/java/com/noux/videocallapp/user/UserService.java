package com.noux.videocallapp.user;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void register(User user ){
        user.setStatus("online");
        userRepository.save(user);
    }

    public User login(User user){
        var existUser= userRepository.findByEmail(user.getEmail());
        if ((!existUser.getPassword().equals(user.getPassword()))){
            throw new RuntimeException("password incorrect ");
        }
        existUser.setStatus("online");
        userRepository.save(existUser);
        return existUser;
    }

    public List<User> getConnectedUsers(){
        String status = "online";
       return userRepository.findAllByStatusContainingIgnoreCase(status);
    }

    @Transactional
    public void logout(User user){
        var connectedUser = userRepository.findByEmail(user.getEmail());
            connectedUser.setStatus("offline");
            userRepository.save(connectedUser);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
