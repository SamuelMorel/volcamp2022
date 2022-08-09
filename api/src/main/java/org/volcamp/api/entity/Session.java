package org.volcamp.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.graphql.Ignore;
import org.volcamp.api.entity.generic.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.ws.rs.QueryParam;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Session extends AbstractEntity {
    @QueryParam("startTime")
    private LocalDateTime startTime;
    @QueryParam("endTime")
    private LocalDateTime endTime;

    @ManyToOne
    @JsonIgnoreProperties("sessions")
    private Talk talk;

    @ManyToOne
    @JsonIgnoreProperties("sessions")
    private Room room;

    @ManyToOne
    @JsonIgnoreProperties("sessions")
    private ConferenceType conferenceType;

    // Search purpose, do not save
    @Transient
    // Ignore on getter means no output for this field
    @Getter(onMethod_ = {@Ignore, @JsonIgnore})
    private LocalDateTime startTimeBefore;

    @Transient
    @Getter(onMethod_ = {@Ignore, @JsonIgnore})
    private LocalDateTime startTimeAfter;

    @Transient
    @Getter(onMethod_ = {@Ignore, @JsonIgnore})
    private LocalDateTime endTimeBefore;

    @Transient
    @Getter(onMethod_ = {@Ignore, @JsonIgnore})
    private LocalDateTime endTimeAfter;
}
