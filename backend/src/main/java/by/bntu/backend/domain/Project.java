package by.bntu.backend.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
@Data
@EqualsAndHashCode(of = "id")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String name;

    @JsonView(Views.FullProject.class)
    private String description;

    @OneToMany(mappedBy = "project")
    @JsonView(Views.FullProject.class)
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany(mappedBy = "projects")
    private Set<User> users = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @JsonView(Views.IdName.class)
    private Status status;
}
