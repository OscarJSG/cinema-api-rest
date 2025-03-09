package com.cine.reservation.service;

import com.cine.reservation.dto.RoomDTO;
import com.cine.reservation.mapper.RoomMapper;
import com.cine.reservation.model.Movie;
import com.cine.reservation.model.Room;
import com.cine.reservation.repository.MovieRepository;
import com.cine.reservation.repository.RoomRepository;
import com.cine.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;
    private final ReservationRepository reservationRepository;
    private final RoomMapper roomMapper;

    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<RoomDTO> getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(roomMapper::toDTO);
    }

    public List<RoomDTO> getAvailableRooms(Long movieId, String schedule) {
        Movie movie = movieRepository.findByMovieIdAndSchedule(movieId, schedule)
                .orElseThrow(() -> new IllegalArgumentException("Horario no válido para esta película"));

        return movie.getRooms().stream()
                .filter(room -> isRoomAvailable(room, schedule))
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());
    }

    private boolean isRoomAvailable(Room room, String schedule) {
        long reservedSeats = reservationRepository.countByRoomAndSchedule(room, schedule);
        return reservedSeats < room.getCapacity();
    }
}
