package com.cine.reservation.controller;

import com.cine.reservation.dto.RoomDTO;
import com.cine.reservation.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
@RequiredArgsConstructor

public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {
        RoomDTO room = roomService.getRoomById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sala no encontrada"));
        return ResponseEntity.ok(room);
    }

    @GetMapping(params = {"movieId", "schedule"})
    public ResponseEntity<List<RoomDTO>> getAvailableRooms(@RequestParam Long movieId, @RequestParam String schedule) {
        return ResponseEntity.ok(roomService.getAvailableRooms(movieId, schedule));
    }
}
