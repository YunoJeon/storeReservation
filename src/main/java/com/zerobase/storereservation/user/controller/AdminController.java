package com.zerobase.storereservation.user.controller;

import com.zerobase.storereservation.user.entity.User;
import com.zerobase.storereservation.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    // http://localhost:8080/admin/assign?userId=(userId)
    @Operation(summary = "user_role 변경", description = "유저 상태 변경 API 입니다. userId 입력 시 role 이 true 로 변경되어 파트너가 됩니다.")
    @PostMapping("/assign")
    public ResponseEntity<User> assignUser(@RequestParam Long userId) {
        userService.assignRole(userId);

        return ResponseEntity.ok().build();
    }
}
