package by.bntu.backend.controller.convert.user;

import by.bntu.backend.domain.User;
import by.bntu.backend.dto.UserRequestDto;
import org.springframework.stereotype.Component;

@Component
public class UserCreateRequestConverter extends UserRequestConverter<UserRequestDto, User> {

    @Override
    public User convert(UserRequestDto request) {
        User user = new User();
        return doConvert(request, user);
    }
}