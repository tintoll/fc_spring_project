package io.tintoll.eatgo.interfaces;

import io.tintoll.eatgo.application.UserService;
import io.tintoll.eatgo.domain.User;
import io.tintoll.eatgo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SessionController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/session")
    public ResponseEntity<?> create(@RequestBody SessionRequestDto resource) throws URISyntaxException {

        String email = resource.getEmail();
        String password = resource.getPassword();
        User user = userService.autheticate(email, password);

        String accessToken = jwtUtil.createToken(user.getId(),
                user.getName(),
                user.isRestaurantOwner() ? user.getRestaurantId() :  null
                );

        return ResponseEntity.created(new URI("/session")).body(
                SessionResponseDto.builder().accessToken(accessToken).build());
    }
}
