package com.sin.orb.security.oauth2.user;

import com.sin.orb.exception.OAuth2AuthenticationProcessingException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyMap;

class OAuth2UserInfoFactoryTest {

    @Test
    void getOAuth2UserInfoShouldReturnCorrectUserInfo() {
        String google = "google";
        String facebook = "facebook";

        OAuth2UserInfo googleUserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(google, anyMap());
        OAuth2UserInfo facebookUserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(facebook, anyMap());

        assertThat(googleUserInfo).isInstanceOf(GoogleOAuth2UserInfo.class);
        assertThat(facebookUserInfo).isInstanceOf(FacebookOAuth2UserInfo.class);
    }

    @Test
    void whenGetOAuth2UserInfoGetsUnsupportedRegistrationIdThanThrowException() {
        String unsupportedId = "unsupported";

        assertThrows(OAuth2AuthenticationProcessingException.class,
                     () -> OAuth2UserInfoFactory.getOAuth2UserInfo(unsupportedId, anyMap()));
    }
}