package com.jhipster.sample.repository;

import com.jhipster.sample.domain.BloodPressure;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BloodPressure entity.
 */
public interface BloodPressureRepository extends JpaRepository<BloodPressure,Long> {

    @Query("select bloodPressure from BloodPressure bloodPressure where bloodPressure.user.login = ?#{principal.username}")
    List<BloodPressure> findByUserIsCurrentUser();

}
