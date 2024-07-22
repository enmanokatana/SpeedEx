package org.example.server.Documentation;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.server.models.Workspace;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@RequiredArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;


    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String filePath;

    @ManyToMany(mappedBy = "workspaces",fetch = FetchType.LAZY)
    private Set<Workspace> workspaces = new HashSet<>();





}
