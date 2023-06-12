package com.glo.app.service.impl;

import com.glo.app.entity.Role;
import com.glo.app.entity.User;
import com.glo.app.exception.EmployeeApiException;
import com.glo.app.payload.LoginDto;
import com.glo.app.payload.RegisterDto;
import com.glo.app.repository.RoleRepository;
import com.glo.app.repository.UserRepository;
import com.glo.app.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    //to create the token for authentication process
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    //Constructor based dependency injection
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authenticate =
                authenticationManager.
                        authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return "User logged in successfully!...";
    }

    @Override
    public String register(RegisterDto registerDto) {
        //check username
        if(userRepository.existsByUsername(registerDto.getUsername()))
            throw new EmployeeApiException(HttpStatus.BAD_REQUEST,"Username already exists");

        //check email
        if(userRepository.existsByEmail(registerDto.getEmail()))
            throw new EmployeeApiException(HttpStatus.BAD_REQUEST,"Email already exists");

        //set the user object
        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        //set the role from role entity
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER").get();
        roles.add(role);
        user.setRole(roles);

        //user is registered in the database
        userRepository.save(user);

        return "User registered successfully";
    }
}
