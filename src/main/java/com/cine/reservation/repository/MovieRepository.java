package com.cine.reservation.repository;

import com.cine.reservation.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);
    boolean existsByTitle(String title);

    @Query("SELECT m FROM Movie m WHERE m.id = :movieId AND :schedule IN elements(m.schedules)")
    Optional<Movie> findByMovieIdAndSchedule(@Param("movieId") Long movieId, @Param("schedule") String schedule);
}
