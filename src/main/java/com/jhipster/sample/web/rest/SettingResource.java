package com.jhipster.sample.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jhipster.sample.domain.Setting;
import com.jhipster.sample.repository.SettingRepository;
import com.jhipster.sample.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Setting.
 */
@RestController
@RequestMapping("/api")
public class SettingResource {

    private final Logger log = LoggerFactory.getLogger(SettingResource.class);
        
    @Inject
    private SettingRepository settingRepository;
    
    /**
     * POST  /settings -> Create a new setting.
     */
    @RequestMapping(value = "/settings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Setting> createSetting(@Valid @RequestBody Setting setting) throws URISyntaxException {
        log.debug("REST request to save Setting : {}", setting);
        if (setting.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("setting", "idexists", "A new setting cannot already have an ID")).body(null);
        }
        Setting result = settingRepository.save(setting);
        return ResponseEntity.created(new URI("/api/settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("setting", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /settings -> Updates an existing setting.
     */
    @RequestMapping(value = "/settings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Setting> updateSetting(@Valid @RequestBody Setting setting) throws URISyntaxException {
        log.debug("REST request to update Setting : {}", setting);
        if (setting.getId() == null) {
            return createSetting(setting);
        }
        Setting result = settingRepository.save(setting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("setting", setting.getId().toString()))
            .body(result);
    }

    /**
     * GET  /settings -> get all the settings.
     */
    @RequestMapping(value = "/settings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Setting> getAllSettings() {
        log.debug("REST request to get all Settings");
        return settingRepository.findAll();
            }

    /**
     * GET  /settings/:id -> get the "id" setting.
     */
    @RequestMapping(value = "/settings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Setting> getSetting(@PathVariable Long id) {
        log.debug("REST request to get Setting : {}", id);
        Setting setting = settingRepository.findOne(id);
        return Optional.ofNullable(setting)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /settings/:id -> delete the "id" setting.
     */
    @RequestMapping(value = "/settings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSetting(@PathVariable Long id) {
        log.debug("REST request to delete Setting : {}", id);
        settingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("setting", id.toString())).build();
    }
}
