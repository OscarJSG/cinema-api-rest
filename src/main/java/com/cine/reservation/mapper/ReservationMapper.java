package com.cine.reservation.mapper;

import com.cine.reservation.dto.ReservationDTO;
import com.cine.reservation.model.Movie;
import com.cine.reservation.model.Reservation;
import com.cine.reservation.model.Room;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    public ReservationDTO toDTO(Reservation reservation) {
        return new ReservationDTO(
                reservation.getId(),
                reservation.getMovie().getId(),
                reservation.getRoom().getId(),
                reservation.getSchedule(),
                reservation.getSeats(),
                reservation.getEmail()
        );
    }

    public Reservation toEntity(ReservationDTO dto, Movie movie, Room room) {
        Reservation reservation = new Reservation();
        reservation.setMovie(movie);
        reservation.setRoom(room);
        reservation.setSchedule(dto.getSchedule());
        reservation.setSeats(dto.getSeats());
        reservation.setEmail(dto.getEmail());
        return reservation;
    }
}