package org.example.server.Documentation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface DocumentRepository extends JpaRepository<Document,Long> {
       List<Document> findByCategory(Category category);
       List<Document> findByNameContainingIgnoreCase(String name);

}
