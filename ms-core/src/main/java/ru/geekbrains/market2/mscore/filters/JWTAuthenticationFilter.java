package ru.geekbrains.market2.mscore.filters;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.geekbrains.market2.mscore.exceptions.InvalidTokenException;
import ru.geekbrains.market2.mscore.model.entities.UserInfo;
import ru.geekbrains.market2.mscore.repository.RedisRepository;
import ru.geekbrains.market2.mscore.services.JWTTokenService;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTTokenService tokenService;

    private final RedisRepository redisRepository;

    public JWTAuthenticationFilter(JWTTokenService tokenService, RedisRepository redisRepository) {
        this.tokenService = tokenService;
        this.redisRepository = redisRepository;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        if (authorizationHeaderIsInvalid(authorizationHeader)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = checkToken(authorizationHeader);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean authorizationHeaderIsInvalid(String authorizationHeader) {
        return authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ");
    }

    private UsernamePasswordAuthenticationToken checkToken(String authorizationHeader) throws ExpiredJwtException {
        String token = authorizationHeader.replace("Bearer ", "");

        if (redisRepository.getToken(token)!=null) throw new InvalidTokenException("");

        UserInfo userInfo = tokenService.parseToken(token);

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (userInfo.getRole() != null && !userInfo.getRole().isEmpty()) {
            authorities.add(new SimpleGrantedAuthority(userInfo.getRole()));
        }

        return new UsernamePasswordAuthenticationToken(userInfo, null, authorities);
    }
}
