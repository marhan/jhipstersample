package com.jhipster.sample.repository;

import com.jhipster.sample.domain.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the Point entity.
 */
public interface PointRepository extends JpaRepository<Point, Long> {

    @Query("select point from Point point where point.user.login = ?#{principal.username}")
    List<Point> findByUserIsCurrentUser();

    @Query("select point from Point point where point.user.login = ?#{principal.username}")
    Page<Point> findAllForCurrentUser(Pageable pageable);

    List<Point> findAllByDateBetween(LocalDate firstDate, LocalDate secondDate);
}
