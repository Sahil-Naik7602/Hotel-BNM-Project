package com.example.hotelbnmproject.controller;

import com.example.hotelbnmproject.dto.LoginRequestDto;
import com.example.hotelbnmproject.dto.LoginResponseDto;
import com.example.hotelbnmproject.dto.SignUpRequestDto;
import com.example.hotelbnmproject.dto.UserDto;
import com.example.hotelbnmproject.security.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(SignUpRequestDto signUpRequestDto){
        UserDto userDto = authService.signUp(signUpRequestDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> signUp(LoginRequestDto loginRequestDto, HttpServletResponse response){
        String[] tokens = authService.login(loginRequestDto);

        Cookie cookie = new Cookie("refreshToken",tokens[1]);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(new LoginResponseDto(tokens[0]));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request){
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(Cookie->"refreshToken".equals(Cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(()->new AuthenticationServiceException("refresh Token not found inside the Cookies"));
        LoginResponseDto responseDto = authService.refresh(refreshToken);

        return ResponseEntity.ok(responseDto);
    }


}
