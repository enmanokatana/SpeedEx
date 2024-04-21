package org.example.server.repositories;

import org.example.server.models.Option;
import org.example.server.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OptionRepository extends JpaRepository<Option,Long> {
    Optional<List<Option>> findByQuestionId(Long questionId);
}
