package com.cine.reservation.mapper;

import com.cine.reservation.dto.RoomDTO;
import com.cine.reservation.model.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    public RoomDTO toDTO(Room room) {
        return new RoomDTO(
                room.getId(),
                room.getName(),
                room.getCapacity()
        );
    }
}
