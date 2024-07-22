package org.example.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.server.Documentation.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Workspace implements Serializable {

    @Serial
    private static final long serialVersionUID = 32311313L;
    @Id
    @GeneratedValue
    private Long id;


    private String name;

    private String description;



    private String code;

    @ManyToOne
    @JsonIgnore
    private User admin;

    @ManyToMany
    @JsonIgnore
    private List<User> users;

    @OneToMany(mappedBy = "workspace",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private  List<Exam> exams;

    private String image;

    @ManyToMany(mappedBy = "workspaces")
    private Set<Document> documents = new HashSet<>();

    @Override
    public String toString() {
        return "Workspace{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", admin=" + admin +
                '}';
    }
}
