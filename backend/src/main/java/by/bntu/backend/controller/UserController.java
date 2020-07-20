package by.bntu.backend.controller;

import by.bntu.backend.domain.User;
import by.bntu.backend.domain.Views;
import by.bntu.backend.repository.UserRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/usercreds")
    @JsonView(Views.FullProfile.class)
    public User getUserCreds(Authentication authentication) {
        System.out.println(authentication.getDetails());
        User byUsername = userRepo.findByUsername(authentication.getName());
        return byUsername;
    }
}
