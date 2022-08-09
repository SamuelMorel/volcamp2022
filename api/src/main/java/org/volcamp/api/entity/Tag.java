package org.volcamp.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.volcamp.api.entity.generic.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.ws.rs.QueryParam;
import java.util.List;

@Getter
@Setter
@Entity
public class Tag extends AbstractEntity {
    @Column(name = "TAG_VALUE")
    @QueryParam("value")
    private String value;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnoreProperties("tags")
    private List<Talk> talks;
}
