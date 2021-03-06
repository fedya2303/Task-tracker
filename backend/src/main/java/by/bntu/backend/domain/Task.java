package by.bntu.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "task")
@Data
@EqualsAndHashCode(of = "id")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String name;

    @JsonView(Views.FullTask.class)
    private String description;

    @OneToMany(mappedBy = "task", orphanRemoval = true)
    @JsonView(Views.FullTask.class)
    @JsonManagedReference
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;

    @Enumerated(EnumType.STRING)
    @JsonView(Views.Id.class)
    private Status status;
}
