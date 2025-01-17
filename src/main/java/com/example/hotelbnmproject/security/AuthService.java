package com.example.hotelbnmproject.security;

import com.example.hotelbnmproject.dto.LoginRequestDto;
import com.example.hotelbnmproject.dto.LoginResponseDto;
import com.example.hotelbnmproject.dto.SignUpRequestDto;
import com.example.hotelbnmproject.dto.UserDto;
import com.example.hotelbnmproject.entity.User;
import com.example.hotelbnmproject.entity.enums.Role;
import com.example.hotelbnmproject.exception.ResourceNotFoundException;
import com.example.hotelbnmproject.repository.UserRepository;
import com.example.hotelbnmproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;

    public UserDto signUp(SignUpRequestDto signUpRequestDto){
        User user = userRepository.findByEmail(signUpRequestDto.getEmail());
        if (user != null){
            throw new RuntimeException("Email already exists kindly login");
        }
        User newUser = modelMapper.map(signUpRequestDto,User.class);
        newUser.setRoles(Set.of(Role.GUEST));
        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        newUser = userRepository.save(newUser);

        return modelMapper.map(newUser,UserDto.class);
    }

    public String[] login(LoginRequestDto loginRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),loginRequestDto.getPassword())
        );
        User user = (User) authentication.getPrincipal();
        String[] response = new String[2];

        response[0] = jwtService.generateAccessToken(user);
        response[1] = jwtService.generateRefreshToken(user);
        return response;

    }

    public LoginResponseDto refresh(String refreshToken) {

        Long id  = jwtService.getUserIdFromToken(refreshToken);
        User user = userService.findUserById(id);

        String accessToken = jwtService.generateAccessToken(user);
        return new LoginResponseDto(accessToken);
    }

    public UserDto assignHotelManager(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found for ID: "+id));
        Set<Role> roles = user.getRoles();
        roles.add(Role.HOTEL_MANAGER);
        user.setRoles(roles);
        userRepository.save(user);
        return modelMapper.map(user,UserDto.class);
    }
}
