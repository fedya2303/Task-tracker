package by.bntu.backend.controller.convert.user;

import by.bntu.backend.controller.convert.EntityConverter;
import by.bntu.backend.domain.Role;
import by.bntu.backend.domain.User;
import by.bntu.backend.dto.UserRequestDto;

public abstract class UserRequestConverter<S, T> extends EntityConverter<S, T> {

    protected User doConvert(UserRequestDto request, User user) {
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.getRoles().add(Role.valueOf(request.getRole()));
        return user;
    }
}