package com.study.security.config.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행 시킴
// 로그인 진행이 완료 되면 시큐리티 session을 만들어 준다. (Security ContextHelder)
// 오브젝트 => Authentication 타입 객체 안에 만들어 줌 => 여기 안에 User 정보가 있어야 됨 => User 오브젝트타입은 UserDetails 타입 객체

import com.study.security.model.User;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// Securiy Session => Authentication => UserDetails
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 user의 권한을 리턴하는 곳임
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> user.getRole());
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 예) 1년동안 회원 로그인 하지 않으면 휴면계정 전환 등 이런거 할때 사용
        return true;
    }
}
