package com.example.hotelbnmproject.service;

import com.example.hotelbnmproject.entity.User;
import com.example.hotelbnmproject.exception.ResourceNotFoundException;
import com.example.hotelbnmproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User with id: "+id+" not found" ));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }
}
