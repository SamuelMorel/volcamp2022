package org.volcamp.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.volcamp.api.entity.generic.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.ws.rs.QueryParam;
import java.util.List;

@Getter
@Setter
@Entity
public class Talk extends AbstractEntity {
    @QueryParam("title")
    private String title;

    @ManyToMany
    @JsonIgnoreProperties("talks")
    private List<Author> authors;

    @ManyToMany
    @JsonIgnoreProperties("talks")
    private List<Tag> tags;

    @OneToMany(mappedBy = "talk")
    @JsonIgnoreProperties("talk")
    private List<Session> sessions;
}
