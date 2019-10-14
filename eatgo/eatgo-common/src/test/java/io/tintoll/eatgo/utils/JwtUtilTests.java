package io.tintoll.eatgo.utils;

import org.junit.Test;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class JwtUtilTests {

    private final String SECRET = "12345678901234567890123456789012";

    @Test
    public void createToken() {

        JwtUtil jwtUtil = new JwtUtil(SECRET);

        String accessToken = jwtUtil.createToken(1004L, "Joker");

        assertThat(accessToken, containsString("."));
    }
}