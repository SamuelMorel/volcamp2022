package org.volcamp.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.volcamp.api.entity.generic.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.ws.rs.QueryParam;
import java.util.List;

@Getter
@Setter
@Entity
public class Author extends AbstractEntity {
    @QueryParam("firstName")
    private String firstName;
    @QueryParam("lastName")
    private String lastName;

    @ManyToMany(mappedBy = "authors")
    @JsonIgnoreProperties("authors")
    private List<Talk> talks;
}
