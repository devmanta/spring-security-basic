package com.study.security.config.oauth;

import com.study.security.config.auth.PrincipalDetails;
import com.study.security.config.oauth.provider.FacebookUserInfo;
import com.study.security.config.oauth.provider.GoogleUserInfo;
import com.study.security.config.oauth.provider.NaverUserInfo;
import com.study.security.config.oauth.provider.OAuth2UserInfo;
import com.study.security.model.User;
import com.study.security.repository.UserRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    //oauth2로 로그인한 후 받은 userRequest 데이터에 대한 후처리 되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo;
        String loginFrom = userRequest.getClientRegistration().getRegistrationId();
        if("google".equals(loginFrom)) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if("facebook".equals(loginFrom)) {
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        } else if("naver".equals(loginFrom)) {
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
        } else {
            oAuth2UserInfo = null;
        }

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String username = provider + "_" + providerId;
        String password = bCryptPasswordEncoder.encode("1234");
        String role = "ROLE_USER";

        User user = userRepository.findByUsername(username);
        if(user == null) {
            user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .provider(provider)
                .providerId(providerId)
                .build();
            userRepository.save(user);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }


}
