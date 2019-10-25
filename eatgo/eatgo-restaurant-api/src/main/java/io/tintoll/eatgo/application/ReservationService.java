package io.tintoll.eatgo.application;

import io.tintoll.eatgo.domain.Reservation;
import io.tintoll.eatgo.domain.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReservationService {

    private ReservationRepository reservationRepository;


    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservations(Long restaurantId) {
        return reservationRepository.findAllByRestaurantId(restaurantId);
    }
}
