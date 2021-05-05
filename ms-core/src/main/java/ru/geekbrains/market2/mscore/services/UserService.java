package ru.geekbrains.market2.mscore.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.market2.mscore.model.dtos.UserDto;
import ru.geekbrains.market2.mscore.model.entities.Role;
import ru.geekbrains.market2.mscore.model.entities.User;
import ru.geekbrains.market2.mscore.repositories.RoleRepository;
import ru.geekbrains.market2.mscore.repositories.UserRepository;


import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    //private final UserMapper userMapper;

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public Optional<UserDto> findDtoByUsername(String username) {
        return findByLogin(username).map(UserDto::new);
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public Optional<UserDto> findDtoById(long id) {
        return findById(id).map(UserDto::new);
    }
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        Role role = roleRepository.findByName("ROLE_USER");
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByLoginAndPassword(String login, String password) {
        User userEntity = findByLogin(login).get();
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found",username)));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
                mapRolesToAuthorities(Arrays.asList(user.getRole())));
    }
}
