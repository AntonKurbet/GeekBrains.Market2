package ru.geekbrains.market2.msauth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.market2.mscore.interfaces.ITokenService;
import ru.geekbrains.market2.mscore.model.dtos.AuthRequestDto;
import ru.geekbrains.market2.mscore.model.dtos.AuthResponseDto;
import ru.geekbrains.market2.mscore.model.dtos.SignUpRequestDto;
import ru.geekbrains.market2.msauth.model.entities.User;
import ru.geekbrains.market2.msauth.services.UserService;
import ru.geekbrains.market2.mscore.model.entities.UserInfo;


@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private ITokenService tokenService;

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
                .userEmail(user.getLogin())
                .role(user.getRole().getName())
                .build();
        String token = tokenService.generateToken(userInfo);
        return new AuthResponseDto(token);
    }
}