package org.example.server.repositories;

import org.example.server.models.User;
import org.example.server.models.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {


    List<Workspace> findByAdmin(User user);

    boolean existsByCode(String code);

    Optional<Workspace> findByCode(String code);
}
