package com.zerobase.storereservation.user.controller;

import com.zerobase.storereservation.user.dto.LoginDto;
import com.zerobase.storereservation.user.dto.UserDto;
import com.zerobase.storereservation.user.entity.User;
import com.zerobase.storereservation.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // http://localhost:8080/user
    @Operation(summary = "회원가입", description = "회원가입 API 입니다. 기본 user_role 은 false 입니다.")
    @PostMapping
    public ResponseEntity<UserDto> registerUser(@RequestBody User user) {

        UserDto userDto = userService.register(user);

        return ResponseEntity.ok(userDto);
    }

    // http://localhost:8080/user/login
    @Operation(summary = "로그인", description = "로그인 API 입니다. 로그인 아이디로 휴대폰 번호를 사용합니다.")
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto) {

        User user = userService.login(loginDto.getPhoneNumber(), loginDto.getPassword());

        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.isRole())
                .build();

        return ResponseEntity.ok(userDto);
    }

    // http://localhost:8080/user/logout
    @Operation(summary = "로그아웃", description = "로그아웃 API 입니다.")
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        return ResponseEntity.ok("정상적으로 로그아웃 되었습니다.");
    }
}
