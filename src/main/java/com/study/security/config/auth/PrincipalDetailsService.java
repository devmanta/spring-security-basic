package com.study.security.config.auth;

import com.study.security.model.User;
import com.study.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 .loginProcessingUrl("/login") 설정해줘서 /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loaduserByUsername 함수가 실행 됨
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // security session = Authentication = UserDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //파라미터 username 변수명 = 폼태그의 name 값

        User user = userRepository.findByUsername(username);
        if(user != null) {
            return new PrincipalDetails(user);
        }

        return null; //return 되면 Authentication 내부의 UserDetails로 들어감
    }

}
