package ru.geekbrains.market2.msauth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.market2.mscore.interfaces.ITokenService;
import ru.geekbrains.market2.msauth.model.entities.User;
import ru.geekbrains.market2.msauth.services.UserService;
import ru.geekbrains.market2.mscore.model.entities.UserInfo;
import ru.geekbrains.market2.mscore.repository.RedisRepository;
import ru.geekbrains.market2.routing.dtos.AuthRequestDto;
import ru.geekbrains.market2.routing.dtos.AuthResponseDto;
import ru.geekbrains.market2.routing.dtos.SignUpRequestDto;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private RedisRepository redisRepository;

    @PostMapping("/signup")
    public String registerUser(@RequestBody SignUpRequestDto signUpRequest) {
        User user = new User();
        user.setPassword(signUpRequest.getPassword());
        user.setLogin(signUpRequest.getLogin());
        user.setEmail(signUpRequest.getEmail());
        userService.saveUser(user);
        return "OK";
    }

    @PostMapping("/signin")
    public AuthResponseDto auth(@RequestBody AuthRequestDto request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        UserInfo userInfo = UserInfo.builder()
                .userId(user.getId())
                .userEmail(user.getEmail())
                .role(user.getRole().getName())
                .build();
        String token = tokenService.generateToken(userInfo);
        return new AuthResponseDto(token);
    }

    @GetMapping("/signout")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String logout(@RequestHeader(name="Authorization") String token) {
        Date expirationDate = tokenService.getExpirationDate(token);
        int ttl = (int) Duration.between(Instant.now(), expirationDate.toInstant()).toSeconds();
        redisRepository.putToken(
                token.replace("Bearer ", ""),
                ttl);
        return "Logged out";
    }

}