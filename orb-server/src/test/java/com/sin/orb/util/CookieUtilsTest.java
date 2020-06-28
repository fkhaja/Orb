package com.sin.orb.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class CookieUtilsTest {
    @Captor
    private ArgumentCaptor<Cookie> captor;

    private final Cookie[] cookies = new Cookie[2];

    @BeforeEach
    void init() {
        Cookie firstStub = new Cookie("name_1", "value_1");
        Cookie secondStub = new Cookie("name_2", "value_2");
        cookies[0] = firstStub;
        cookies[1] = secondStub;
    }

    @Test
    void getCookieReturnCorrectCookie() {
        String cookieName = "name_1";
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getCookies()).thenReturn(cookies);

        Cookie result = CookieUtils.getCookie(request, cookieName).orElse(cookies[0]);

        assertThat(result.getName()).isEqualTo("name_1");
        assertThat(result.getValue()).isEqualTo("value_1");
    }

    @Test
    void addCookieCorrectlyAddCookieToResponse() {
        HttpServletResponse response = mock(HttpServletResponse.class);
        doNothing().when(response).addCookie(any(Cookie.class));

        CookieUtils.addCookie(response, "name", "value", 256);

        verify(response).addCookie(captor.capture());

        Cookie result = captor.getValue();
        assertThat(result.getName()).isEqualTo("name");
        assertThat(result.getValue()).isEqualTo("value");
        assertThat(result.getMaxAge()).isEqualTo(256);
    }

    @Test
    void deleteCookieCorrectlyDeleteCookie() {
        String cookieName = "name_1";
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getCookies()).thenReturn(cookies);
        doNothing().when(response).addCookie(any(Cookie.class));

        CookieUtils.deleteCookie(request, response, cookieName);

        verify(response).addCookie(captor.capture());

        Cookie result = captor.getValue();
        assertThat(result.getName()).isEqualTo(cookieName);
        assertThat(result.getValue()).isEmpty();
        assertThat(result.getMaxAge()).isEqualTo(0);
    }

    @Test
    void serializeCorrectlySerializesString() {
        String str = "text";

        String encodedString = CookieUtils.serialize(str);

        String decodedString = (String) SerializationUtils.deserialize(Base64.getUrlDecoder().decode(encodedString));
        assertThat(str).isEqualTo(decodedString);
    }

    @Test
    void serializeCorrectlySerializesWrappers() {
        Integer intVal = 10;
        Long longVal = 20L;
        Byte byteVal = 1;
        Character charVal = 'w';
        Double doubleVal = 0.15;
        Float floatVal = 0.35f;

        String encodedInt = CookieUtils.serialize(intVal);
        String encodedLong = CookieUtils.serialize(longVal);
        String encodeByte = CookieUtils.serialize(byteVal);
        String encodedChar = CookieUtils.serialize(charVal);
        String encodedDouble = CookieUtils.serialize(doubleVal);
        String encodedFloat = CookieUtils.serialize(floatVal);

        Integer decodedInt = (Integer) SerializationUtils.deserialize(Base64.getUrlDecoder().decode(encodedInt));
        Long decodedLong = (Long) SerializationUtils.deserialize(Base64.getUrlDecoder().decode(encodedLong));
        Byte decodedByte = (Byte) SerializationUtils.deserialize(Base64.getUrlDecoder().decode(encodeByte));
        Character decodedChar = (Character) SerializationUtils.deserialize(Base64.getUrlDecoder().decode(encodedChar));
        Double decodedDouble = (Double) SerializationUtils.deserialize(Base64.getUrlDecoder().decode(encodedDouble));
        Float decodedFloat = (Float) SerializationUtils.deserialize(Base64.getUrlDecoder().decode(encodedFloat));

        assertThat(intVal).isEqualTo(decodedInt);
        assertThat(longVal).isEqualTo(decodedLong);
        assertThat(byteVal).isEqualTo(decodedByte);
        assertThat(charVal).isEqualTo(decodedChar);
        assertThat(doubleVal).isEqualTo(decodedDouble);
        assertThat(floatVal).isEqualTo(decodedFloat);
    }

    @Test
    void serializeCorrectlySerializesArrays() {
        Long[] longArr = {1L, 2L, 3L};
        String[] stringArr = {"val1", "val2", "val3"};

        String encodedLongArr = CookieUtils.serialize(longArr);
        String encodedStringArr = CookieUtils.serialize(stringArr);

        Long[] decodedLongArr = (Long[]) SerializationUtils.deserialize(Base64.getUrlDecoder().decode(encodedLongArr));
        String[] decodedStringArr = (String[]) SerializationUtils.deserialize(Base64.getUrlDecoder()
                                                                                    .decode(encodedStringArr));

        assertThat(longArr).isEqualTo(decodedLongArr);
        assertThat(stringArr).isEqualTo(decodedStringArr);
    }

    @Test
    void deserializeCorrectlyDeserializesCookieValue() {
        String value = "text";
        Cookie cookie = new Cookie("name", CookieUtils.serialize(value));

        String decodedCookieValue = CookieUtils.deserialize(cookie, String.class);

        assertThat(decodedCookieValue).isEqualTo(value);
    }
}