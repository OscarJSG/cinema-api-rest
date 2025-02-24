package com.cine.reservation.controller;

import com.cine.reservation.dto.ReservationDTO;
import com.cine.reservation.service.ReservationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        ReservationDTO createdReservation = reservationService.createReservation(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        ReservationDTO reservation = reservationService.getReservationById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservación no encontrada"));
        return ResponseEntity.ok(reservation);
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<ReservationDTO> reservations = reservationService.getAllReservations();
        return reservations.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reservations);
    }

    @GetMapping("/availability")
    public ResponseEntity<List<String>> getAvailableSeats(@RequestParam Long roomId, @RequestParam String schedule) {
        return ResponseEntity.ok(reservationService.getAvailableSeats(roomId, schedule));
    }
}

