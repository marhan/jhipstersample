package com.jhipster.sample.repository;

import com.jhipster.sample.domain.Setting;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Setting entity.
 */
public interface SettingRepository extends JpaRepository<Setting,Long> {

}
