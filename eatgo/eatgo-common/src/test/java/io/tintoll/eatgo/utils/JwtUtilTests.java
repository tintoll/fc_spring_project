package io.tintoll.eatgo.utils;

import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class JwtUtilTests {

    private final String SECRET = "12345678901234567890123456789012";

    private JwtUtil jwtUtil;

    @Before
    public void setUp() {
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    public void createToken() {
        String accessToken = jwtUtil.createToken(1004L, "Joker");

        assertThat(accessToken, containsString("."));
    }

    @Test
    public void getClaims() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiJKb2tlciJ9.0A1ndCB8wljqkgfHdTywu6ykulanEUq8txjlpglfvBQ";
        Claims claims = jwtUtil.getClaims(token);

        assertThat(claims.get("userId", Long.class),is(1004L));
        assertThat(claims.get("name"),is("Joker"));
    }
}