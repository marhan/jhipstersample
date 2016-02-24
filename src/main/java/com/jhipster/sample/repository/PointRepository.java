package com.jhipster.sample.repository;

import com.jhipster.sample.domain.Point;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Point entity.
 */
public interface PointRepository extends JpaRepository<Point, Long> {

    @Query("select point from Point point where point.user.login = ?#{principal.username}")
    List<Point> findByUserIsCurrentUser();

    @Query("select point from Point point where point.user.login = ?#{principal.username}")
    Page<Point> findAllForCurrentUser(Pageable pageable);
}
