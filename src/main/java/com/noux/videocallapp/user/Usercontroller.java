package com.noux.videocallapp.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class Usercontroller {

    private final UserService userService;

    @PostMapping("/login")
    public User login(@RequestBody User user ){
        return userService.login(user);
    }

    @PostMapping("/register")
    public void register(@RequestBody User user){
        userService.register(user);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody User user){
        userService.logout(user);
    }

    @GetMapping
    public List<User> getAllUsers(){
      return userService.getAllUsers();
    }

    @GetMapping("/connected")
    public List<User> getConnectedUsers(){ return userService.getConnectedUsers(); }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
}
