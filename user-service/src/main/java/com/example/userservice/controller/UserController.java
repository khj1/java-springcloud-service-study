package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.utils.UserMapper;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final Greeting greeting;
    private final UserMapper mapper;
    private final UserService userService;
    private final Environment env;

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getGreeting();
    }

    @GetMapping("/health_check")
    public String status() {
        return "It`s working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time=" + env.getProperty("token.expiration_time");
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        UserDto userDto = mapper.mapRequestToDto(user);
        ResponseUser response = mapper.mapDtoToResponse(userService.createUser(userDto));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> selectUsers() {
        List<ResponseUser> response = userService.getUserByAll().stream()
                .map(mapper::mapEntityToResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> selectUserById(@PathVariable String userId) {
        ResponseUser response = mapper.mapDtoToResponse(userService.getUserByUserId(userId));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
