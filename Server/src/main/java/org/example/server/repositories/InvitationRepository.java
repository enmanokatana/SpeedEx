package org.example.server.repositories;

import org.example.server.models.Exam;
import org.example.server.models.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation,Long> {
    List<Invitation> findAllByUserId(Integer userID);
}
