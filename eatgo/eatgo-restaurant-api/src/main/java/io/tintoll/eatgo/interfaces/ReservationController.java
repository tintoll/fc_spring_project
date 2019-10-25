package io.tintoll.eatgo.interfaces;


import io.jsonwebtoken.Claims;
import io.tintoll.eatgo.application.ReservationService;
import io.tintoll.eatgo.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservations")
    public List<Reservation> list(Authentication authentication) {

        Claims claims = (Claims)authentication.getPrincipal();
        Long restaurantId = claims.get("restaurantId", Long.class);
        List<Reservation> list = reservationService.getReservations(restaurantId);

        return list;
    }
}
