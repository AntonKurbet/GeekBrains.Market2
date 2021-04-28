package ru.geekbrains.market2.mscore.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.geekbrains.market2.mscore.model.entities.User;
import ru.geekbrains.market2.mscore.services.UserService;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByLogin(username).get();
        return CustomUserDetails.fromUserEntityToCustomUserDetails(user);
    }
}