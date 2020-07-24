package by.bntu.backend.controller;

import by.bntu.backend.domain.User;
import by.bntu.backend.domain.Views;
import by.bntu.backend.repository.UserRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/usercreds")
    @JsonView(Views.FullProfile.class)
    public User getUserCreds(Principal principal) {
        User byUsername = userRepo.findByUsername(principal.getName());
        return byUsername;
    }
}
