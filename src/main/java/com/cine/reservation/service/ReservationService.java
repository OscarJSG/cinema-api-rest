package com.cine.reservation.service;

import com.cine.reservation.dto.ReservationDTO;
import com.cine.reservation.mapper.ReservationMapper;
import com.cine.reservation.model.Movie;
import com.cine.reservation.model.Reservation;
import com.cine.reservation.model.Room;
import com.cine.reservation.repository.MovieRepository;
import com.cine.reservation.repository.ReservationRepository;
import com.cine.reservation.repository.RoomRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final ReservationMapper reservationMapper;
    private final EmailService emailService;

    @Transactional
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        if (StringUtils.isBlank(reservationDTO.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio para la reservación.");
        }

        Movie movie = movieRepository.findByMovieIdAndSchedule(reservationDTO.getMovieId(), reservationDTO.getSchedule())
                .orElseThrow(() -> new IllegalArgumentException("Horario no válido para esta película"));

        Room room = roomRepository.findById(reservationDTO.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Sala no encontrada"));

        List<String> reservedSeats = reservationRepository.findReservedSeatsByRoomAndSchedule(room.getId(), reservationDTO.getSchedule());

        validateSeatsAvailability(reservationDTO.getSeats(), reservedSeats);

        Reservation reservation = reservationMapper.toEntity(reservationDTO, movie, room);
        reservationRepository.save(reservation);

        emailService.sendReservationEmail(reservationDTO.getEmail(), reservationDTO);

        return reservationMapper.toDTO(reservation);
    }

    private void validateSeatsAvailability(List<String> seats, List<String> reservedSeats) {
        for (String seat : seats) {
            if (reservedSeats.contains(seat)) {
                throw new IllegalStateException("El asiento " + seat + " ya está reservado");
            }
        }
    }

    public Optional<ReservationDTO> getReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::toDTO);
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<String> getAvailableSeats(Long roomId, String schedule) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Sala no encontrada"));

        List<String> allSeats = generateSeats(room.getCapacity());

        List<String> reservedSeats = reservationRepository.findReservedSeatsByRoomAndSchedule(roomId, schedule);

        return allSeats.stream()
                .filter(seat -> !reservedSeats.contains(seat))
                .collect(Collectors.toList());
    }

    private List<String> generateSeats(int capacity) {
        List<String> seats = new ArrayList<>();
        for (int i = 1; i <= capacity; i++) {
            seats.add("A" + i);  // formato sillas : A1, A2, A3 ...
        }
        return seats;
    }
}

