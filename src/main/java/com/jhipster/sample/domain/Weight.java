package com.jhipster.sample.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Weight.
 */
@Entity
@Table(name = "weight")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Weight implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "date_time", nullable = false)
    private ZonedDateTime dateTime;
    
    @NotNull
    @Column(name = "systolic", nullable = false)
    private Integer systolic;
    
    @NotNull
    @Column(name = "diastolic", nullable = false)
    private Integer diastolic;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }
    
    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getSystolic() {
        return systolic;
    }
    
    public void setSystolic(Integer systolic) {
        this.systolic = systolic;
    }

    public Integer getDiastolic() {
        return diastolic;
    }
    
    public void setDiastolic(Integer diastolic) {
        this.diastolic = diastolic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Weight weight = (Weight) o;
        if(weight.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, weight.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Weight{" +
            "id=" + id +
            ", dateTime='" + dateTime + "'" +
            ", systolic='" + systolic + "'" +
            ", diastolic='" + diastolic + "'" +
            '}';
    }
}
