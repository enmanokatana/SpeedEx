package org.example.server.repositories;

import org.example.server.models.Exam;
import org.example.server.models.Option;
import org.example.server.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam,Long> {
    List<Exam> findByUser(User user);

    List<Exam> findAllByStudentId(Integer id);
}
