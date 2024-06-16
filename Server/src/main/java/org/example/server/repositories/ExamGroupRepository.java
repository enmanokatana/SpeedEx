package org.example.server.repositories;

import org.example.server.models.Exam;
import org.example.server.models.ExamGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ExamGroupRepository extends JpaRepository<ExamGroup,Integer> {
}
