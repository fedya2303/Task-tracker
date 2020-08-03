package by.bntu.backend.service;

import by.bntu.backend.domain.User;
import by.bntu.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;
    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsServiceImpl(UserRepo userRepo, MailSender mailSender, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User applicationUser = userRepo.findByUsername(username);
        if (applicationUser == null) {
            return null;
        }
        return applicationUser;
    }

    public boolean addUser(User newUser) {
        User userFromDb = (User) loadUserByUsername(newUser.getUsername());
        if (userFromDb != null) {
            return false;
        }
        newUser.setActivationCode(UUID.randomUUID().toString());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        userRepo.save(newUser);

        sendMessage(newUser);

        return true;
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello, %s!\n" +
                            "Welcome to Task-tracker. Please, visit next link to activate your account: http://localhost:8081/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if (user == null){
            return false;
        }
        user.setActive(true);
        user.setActivationCode(null);
        userRepo.save(user);
        return true;
    }

}
