package com.alijas.gimhaeswim.config.security;

import com.alijas.gimhaeswim.module.user.enums.UserStatus;
import com.alijas.gimhaeswim.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("아이디를 찾을 수 없습니다."));
        if (user.getStatus().equals(UserStatus.DELETED)) {
            throw new UsernameNotFoundException("탈퇴한 회원입니다.");
        } else if (user.getStatus().equals(UserStatus.INACTIVE)) {
            throw new UsernameNotFoundException("비활성화 된 계정입니다.");
        }

        return new CustomUserDetails(user);
    }
}
