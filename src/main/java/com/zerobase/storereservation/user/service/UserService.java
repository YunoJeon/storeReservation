package com.zerobase.storereservation.user.service;

import com.zerobase.storereservation.common.exception.CustomException;
import com.zerobase.storereservation.user.dto.UserDto;
import com.zerobase.storereservation.user.entity.User;
import com.zerobase.storereservation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.zerobase.storereservation.common.type.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public UserDto register(User user) {

        // 중복 회원가입 방지를 위해 휴대폰번호를 찾는 로직. Unique 로 설정된 휴대폰 번호로 중복 방지
        Optional<User> optionalUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (optionalUser.isPresent()) {
            throw new CustomException(ALREADY_EXISTS_USER);
        }

        // 간단하게 구현한 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        User newUser = User.builder()
                .userName(user.getUserName())
                .password(encodedPassword)
                .phoneNumber(user.getPhoneNumber())
                .role(false)
                .createdAt(LocalDateTime.now())
                .build();

        User saveUser = userRepository.save(newUser);

        return UserDto.builder()
                .id(saveUser.getId())
                .phoneNumber(saveUser.getPhoneNumber())
                .userName(saveUser.getUserName())
                .role(saveUser.isRole())
                .build();
    }

    // 로그인 아이디로는 phoneNumber 사용
    public User login(String phoneNumber, String password) {

        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);

        // 휴대폰 번호가 없거나, 비밀번호가 틀리다면 로그인 실패
        if (optionalUser.isEmpty() || !passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            throw new CustomException(FAILED_LONGIN);
        }

        return optionalUser.get();
    }

    // role(매장 관리자(파트너)면 true / 일반 사용자면 false) 을 설정.
    public void assignRole(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        user.setRole(true);
        userRepository.save(user);
    }
}
