package org.volcamp.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.volcamp.api.entity.generic.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.ws.rs.QueryParam;
import java.util.List;

@Getter
@Setter
@Entity
public class ConferenceType extends AbstractEntity {
    @QueryParam("name")
    private String name;

    @OneToMany(mappedBy = "conferenceType")
    @JsonIgnoreProperties("conferenceType")
    private List<Session> sessions;
}
