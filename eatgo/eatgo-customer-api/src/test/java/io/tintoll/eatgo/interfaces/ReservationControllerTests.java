package io.tintoll.eatgo.interfaces;

import io.tintoll.eatgo.application.ReservationService;
import io.tintoll.eatgo.domain.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void create() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiJKb2tlciJ9.0A1ndCB8wljqkgfHdTywu6ykulanEUq8txjlpglfvBQ";

        Long restaurantId = 369L;
        Long userId = 1004L;
        String name = "Joker";
        String date = "2019-10-11";
        String time = "18:00";
        Integer partySize = 4;

        Reservation mockReservation = Reservation.builder().id(12L).build();
        given(reservationService
                .addReservation(any(),any(),any(),any(),any(),any()))
                .willReturn(mockReservation);

        mvc.perform(post("/restaurants/369/reservations")
                    .header("Authorization" ,"Bearer "+token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"date\":\"2019-10-11\",\"time\":\"18:00\",\"partySize\":4}"))
           .andExpect(status().isCreated());

        verify(reservationService).addReservation(eq(restaurantId), eq(userId), eq(name),
                eq(date), eq(time), eq(partySize));

    }

}