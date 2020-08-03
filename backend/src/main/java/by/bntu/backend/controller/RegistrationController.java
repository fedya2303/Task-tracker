package by.bntu.backend.controller;

import by.bntu.backend.domain.User;
import by.bntu.backend.dto.UserRequestDto;
import by.bntu.backend.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    private final UserDetailsServiceImpl userService;
    private final ConversionService conversionService;

    @Autowired
    public RegistrationController(UserDetailsServiceImpl userService, ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @PostMapping("/registration")
    @Transactional
    public boolean registration(
            @RequestBody UserRequestDto userRequest
    ) {
        User newUser = conversionService.convert(userRequest, User.class);
        return userService.addUser(newUser);
    }

    @GetMapping("/activate/{code}")
    public boolean activate(@PathVariable("code") String code){
        return userService.activateUser(code);
    }
}
