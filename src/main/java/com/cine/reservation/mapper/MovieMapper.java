package com.cine.reservation.mapper;

import com.cine.reservation.dto.MovieDTO;
import com.cine.reservation.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    public MovieDTO toDTO(Movie movie) {
        return new MovieDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getGenre(),
                movie.getDuration(),
                movie.getClassification(),
                movie.getDescription(),
                movie.getSchedules()
        );
    }
}
