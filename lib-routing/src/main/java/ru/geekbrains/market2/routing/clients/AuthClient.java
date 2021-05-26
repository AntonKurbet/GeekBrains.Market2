package ru.geekbrains.market2.routing.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.geekbrains.market2.routing.dtos.AuthRequestDto;
import ru.geekbrains.market2.routing.dtos.AuthResponseDto;
import ru.geekbrains.market2.routing.dtos.SignUpRequestDto;

@FeignClient("ms-auth")
public interface AuthClient {

    @PostMapping("/signup")
    String signUp(@RequestBody SignUpRequestDto signUpRequest);

    @PostMapping("/login")
    AuthResponseDto login(@RequestBody AuthRequestDto request);
}
