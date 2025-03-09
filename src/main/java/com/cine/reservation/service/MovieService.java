package com.cine.reservation.service;

import com.cine.reservation.dto.MovieDTO;
import com.cine.reservation.mapper.MovieMapper;
import com.cine.reservation.repository.MovieRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<MovieDTO> getMovieById(Long id) {
        return movieRepository.findById(id)
                .map(movieMapper::toDTO);
    }

    @Transactional
  public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró la película con ID" + id);
        }
    movieRepository.deleteById(id);
  }
}
