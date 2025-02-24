package com.cine.reservation.repository;

import com.cine.reservation.model.Reservation;
import com.cine.reservation.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    long countByRoomAndSchedule(Room room, String schedule);

    @Query("SELECT s FROM Reservation r JOIN r.seats s WHERE r.room.id = :roomId AND r.schedule = :schedule")
    List<String> findReservedSeatsByRoomAndSchedule(@Param("roomId") Long roomId, @Param("schedule") String schedule);

}