package org.example.server.services;

import org.example.server.Dtos.ExamDto;
import org.example.server.enums.DifficultyLevel;
import org.example.server.models.Question;
import org.example.server.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ExamServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private ExamGroupRepository examGroupRepository;

    @Mock
    private ExamRepository examRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private OptionRepository optionRepository;

    @InjectMocks
    private ExamService examService; // Replace with your actual service class name

    private ExamDto examDto;
    @BeforeEach
    void setUp() {
        examDto = ExamDto.builder()
                .user(1)
                .ExamGroup(1)
                .passed(false)
                .student(null)
                .workspace(1L)
                .difficultyLevel(DifficultyLevel.MEDIUM)
                .name("test Exam")
                .timer(33)
                .id(1L)
                .isPublic(true)
                .passingScore(100)
                .questions(null)
                .randomizeQuestions(true)
                .description("cvbn,;lkjhgfd")
                .build();

        List<Question> questions = new ArrayList<>();
        // Add questions to the list
        examDto.setQuestions(questions);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createExam() {
    }

    @Test
    void createExamNew() {



    }

    @Test
    void findExamsByUser() {
    }

    @Test
    void findExamById() {
    }

    @Test
    void deleteExam() {
    }

    @Test
    void getQuestionIdsByExamId() {
    }

    @Test
    void getUserExamsForProfile() {
    }
}