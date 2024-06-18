package org.example.server.services;

import org.example.server.Dtos.ExamDto;
import org.example.server.enums.DifficultyLevel;
import org.example.server.models.*;
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
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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

    private User user;
    private Workspace workspace;
    private ExamGroup examGroup;
    private ExamDto examDto;
    private ResponseDto responseDto;    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(1);
        workspace = new Workspace();
        workspace.setId(1L);
        examGroup = new ExamGroup();
        examGroup.setId(1);
        examDto = new ExamDto();
        examDto.setUser(1);
        examDto.setWorkspace(1L);
        examDto.setExamGroup(1);
        responseDto = new ResponseDto();
    }

    @AfterEach
    void tearDown() {
    }



    @Test
    void createExamNew_UserDoesNotExist() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ResponseDto response = examService.createExamNew(examDto);

        assertEquals(false, response.isWorked());
        assertEquals("User doesn't Exist", response.getMessage());

        verify(userRepository).findById(1);
        verify(workspaceRepository, never()).findById(any());
        verify(examRepository, never()).save(any());
        verify(examGroupRepository, never()).save(any());
        verify(questionRepository, never()).saveAll(any());
        verify(optionRepository, never()).saveAll(any());
    }

    @Test
    void createExamNew_WorkspaceDoesNotExist() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(workspaceRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseDto response = examService.createExamNew(examDto);

        assertEquals(false, response.isWorked());
        assertEquals("WorkSpace doesn't Exist", response.getMessage());

        verify(userRepository).findById(1);
        verify(workspaceRepository).findById(1L);
        verify(examRepository, never()).save(any());
        verify(examGroupRepository, never()).save(any());
        verify(questionRepository, never()).saveAll(any());
        verify(optionRepository, never()).saveAll(any());
    }

    @Test
    void createExamNew_ExamGroupDoesNotExist() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(examGroupRepository.findById(1)).thenReturn(Optional.empty());
        when(examGroupRepository.save(any(ExamGroup.class))).thenReturn(examGroup);
        when(examRepository.save(any(Exam.class))).thenAnswer(i -> i.getArgument(0));

        ResponseDto response = examService.createExamNew(examDto);

        assertEquals(true, response.isWorked());
        assertEquals("saved exam Successfully", response.getMessage());

        verify(userRepository).findById(1);
        verify(workspaceRepository).findById(1L);
        verify(examGroupRepository).findById(1);
        verify(examGroupRepository).save(any(ExamGroup.class));
        verify(examRepository).save(any(Exam.class));
        verify(questionRepository, never()).saveAll(any());
        verify(optionRepository, never()).saveAll(any());
    }

    @Test
    void createExamNew_SuccessWithQuestionsAndOptions() {
        List<Question> questions = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        Question question = new Question();
        question.setOptions(options);
        questions.add(question);
        examDto.setQuestions(questions);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(examGroupRepository.findById(1)).thenReturn(Optional.of(examGroup));
        when(examRepository.save(any(Exam.class))).thenAnswer(i -> i.getArgument(0));
        when(questionRepository.saveAll(questions)).thenReturn(questions);
        when(optionRepository.saveAll(options)).thenReturn(options);

        ResponseDto response = examService.createExamNew(examDto);

        assertTrue(response.isWorked());
        assertEquals("saved exam Successfully", response.getMessage());

        verify(userRepository).findById(1);
        verify(workspaceRepository).findById(1L);
        verify(examGroupRepository).findById(1);
        verify(examRepository).save(any(Exam.class));
        verify(questionRepository).saveAll(questions);
        verify(optionRepository).saveAll(options);
    }


}