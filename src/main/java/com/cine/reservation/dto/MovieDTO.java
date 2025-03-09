package com.cine.reservation.dto;

import com.cine.reservation.model.enums.Genre;
import com.cine.reservation.model.enums.Classification;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Long id;
    private String title;
    private Genre genre;
    private int duration;
    private Classification classification;
    private String description;
    private List<String> schedules;
}
