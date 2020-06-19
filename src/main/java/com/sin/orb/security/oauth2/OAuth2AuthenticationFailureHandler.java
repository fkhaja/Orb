package com.sin.orb.security.oauth2;

import com.sin.orb.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.sin.orb.security.oauth2.OAuth2AuthorizationRequestRepository.REDIRECT_URI_COOKIE;

@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final OAuth2AuthorizationRequestRepository requestRepository;

    @Autowired
    public OAuth2AuthenticationFailureHandler(OAuth2AuthorizationRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String targetUrl = CookieUtils.getCookie(request, REDIRECT_URI_COOKIE).map(Cookie::getValue).orElse(("/"));
        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                                        .queryParam("error", exception.getLocalizedMessage())
                                        .build().toUriString();

        requestRepository.removeAuthorizationRequestCookies(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}