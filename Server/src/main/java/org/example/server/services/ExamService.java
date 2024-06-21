package org.example.server.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.ExamDto;
import org.example.server.exceptions.UserDoesNotExistException;
import org.example.server.exceptions.WorkSpaceDoesNotExistException;
import org.example.server.models.*;
import org.example.server.repositories.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// A lot of stuff to fix
//add the exam





@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepository repository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    private final ExamGroupRepository examGroupRepository;

    private final ResponseDto responseDto =new ResponseDto();

    @Deprecated
    public ResponseDto createExam(ExamDto examDto){

        var user = userRepository.findById(examDto.getUser());
        User student = null;
        if (examDto.getStudent() !=0){
        var studentO =userRepository.findById(examDto.getStudent());
        student = studentO.get();
        }
        var workspace = workspaceRepository.findById(examDto.getWorkspace());

        List<Integer> workspaceUserId =  new ArrayList<>();


        if (workspace.isPresent()){

            if (examDto.getStudent()==0){
                for (User users:workspace.get().getUsers()){
                    workspaceUserId.add(users.getId());
                }
            }



            if (user.isPresent() ){
                List<Question> questions = null;


                if (examDto.getQuestions() != null) {
                    questions = examDto.getQuestions();
                }

                responseDto.setResult(repository.save(Exam.builder()
                        .description(examDto.getDescription())
                        .isPublic(examDto.isPublic())
                        .randomizeQuestions(examDto.isRandomizeQuestions())
                        .passingScore(examDto.getPassingScore())
                        .createdOn(LocalDateTime.now())
                        .timer(examDto.getTimer())
                        .name(examDto.getName())
                        .difficultyLevel(examDto.getDifficultyLevel())
                        .id(0L)
                        .questions(questions)
                        .user(user.get())
                        .workspace(workspace.get())
                        .passed(false)
                        .student(student)
                        .result(false)
                        .passingDate(LocalDateTime.now())
                        .build()));

                responseDto.setMessage("saved exam Successfully ");
                responseDto.setWorked(true);

                assert questions != null;
                for (Question question : questions) {
                    question.setExam((Exam) responseDto.getResult());

                }
                var res = questionRepository.saveAll(questions);

                for (Question obj:res){
                    var ops = obj.getOptions();
                    if (ops!=null){
                        for (Option option:ops){
                            option.setQuestion(obj);
                        }
                        optionRepository.saveAll(ops);

                    }
                }


                for (Integer id : workspaceUserId) {

                    createExam(ExamDto.builder()
                            .user(examDto.getUser())
                            .student(id)
                            .description(examDto.getDescription())
                            .timer(examDto.getTimer())
                            .randomizeQuestions(examDto.isRandomizeQuestions())
                            .name(examDto.getName())
                            .workspace(examDto.getWorkspace())
                            .questions(examDto.getQuestions())
                            .passingScore(examDto.getPassingScore())
                            .isPublic(examDto.isPublic())
                            .difficultyLevel(examDto.getDifficultyLevel())
                            .build());
                }

                return responseDto;

            }
            responseDto.setWorked(false);
            responseDto.setResult(null);
            responseDto.setMessage("User doesn't Exist");
            return responseDto;
        }

                responseDto.setWorked(false);
                responseDto.setResult(null);
                responseDto.setMessage("WorkSpace doesn't Exist");
                return responseDto;



    }
    public ResponseDto createExamNew(ExamDto examDto){

        Optional<User> user = userRepository.findById(examDto.getUser());
        if (user.isEmpty()){
            responseDto.setWorked(false);
            responseDto.setResult(null);
            responseDto.setMessage("User doesn't Exist");
            return responseDto;
        }

        Optional<Workspace> workspace = workspaceRepository.findById(examDto.getWorkspace());
        if (workspace.isEmpty()){
            responseDto.setWorked(false);
            responseDto.setResult(null);
            responseDto.setMessage("WorkSpace doesn't Exist");
            return responseDto;
        }

        Optional<ExamGroup> examGroupOptional = examGroupRepository.findById(examDto.getExamGroup());
        ExamGroup examGroup;
        if (examGroupOptional.isPresent()){
            examGroup = examGroupOptional.get();

        }else {
            examGroup = examGroupRepository.save(ExamGroup.builder()
                    .exams(null)
                    .id(0).build());
        }


        Optional<User> studentOp = userRepository.findById(examDto.getStudent());
        User student = studentOp.orElse(null);

        List<Question> questions = examDto.getQuestions();


        Exam result  = repository.save(
                Exam.builder()
                        .description(examDto.getDescription())
                        .isPublic(examDto.isPublic())
                        .randomizeQuestions(examDto.isRandomizeQuestions())
                        .passingScore(examDto.getPassingScore())
                        .createdOn(LocalDateTime.now())
                        .timer(examDto.getTimer())
                        .name(examDto.getName())
                        .difficultyLevel(examDto.getDifficultyLevel())
                        .id(0L)
                        .questions(questions)
                        .user(user.get())
                        .workspace(workspace.get())
                        .passed(false)
                        .student(student)
                        .examGroup(examGroup)
                        .result(false)
                        .passingDate(examDto.getPassingDate())
                        .build()
        );
        //add teh exam to the group




        if (examGroup.getExams() ==  null){
            var Exams = new ArrayList<Exam>();
            Exams.add(result);
            examGroup.setExams(Exams);
        }else{

            var Exams = examGroup.getExams();
            Exams.add(result);
            examGroup.setExams(Exams);
        }



        examGroupRepository.save(examGroup);

        responseDto.setResult(result);
        responseDto.setWorked(true);
        responseDto.setMessage("saved exam Successfully ");
        //##########################################################################
        //we've Created the exam
        // ##########################################################################

        if (questions!=null){
            for(Question question:questions){
                question.setExam((Exam) responseDto.getResult());
            }
            List<Question> questionResults = questionRepository.saveAll(questions);

            for (Question question:questionResults){
                List<Option> options= question.getOptions();
                if (options != null){
                    for (Option option:options){
                        option.setQuestion(question);
                    }
                    optionRepository.saveAll(options);
                }
            }
        }
        //##########################################################################
        //we've Saved the questions with their options
        // ##########################################################################


        List<Integer> Ids = new ArrayList<>();

        if (student == null){
            for (User userW:workspace.get().getUsers()){
                Ids.add(userW.getId());
            }
        }

        for (Integer id : Ids){
            createExamNew(ExamDto.builder()
                    .user(examDto.getUser())
                    .student(id)
                    .description(examDto.getDescription())
                    .timer(examDto.getTimer())
                    .randomizeQuestions(examDto.isRandomizeQuestions())
                    .name(examDto.getName())
                    .workspace(examDto.getWorkspace())
                    .questions(examDto.getQuestions())
                    .passingScore(examDto.getPassingScore())
                    .isPublic(examDto.isPublic())
                    .difficultyLevel(examDto.getDifficultyLevel())
                    .ExamGroup(examGroup.getId())
                    .passingDate(examDto.getPassingDate())
                    .build());
        }




        //##########################################################################
        //we've Assigned this exam to every user in the
        // workspace and Identified this particular one with exam group
        // ##########################################################################

        return responseDto;
    }

//    public ResponseDto CorrectExam (Exam exam){
//
//    }

    @Cacheable(cacheNames = "exams",key = "#id")
    public ResponseDto findExamsByUser(Integer id){
        var user = userRepository.findById(id);
        if (user.isPresent()){
            List<Exam> exams = repository.findByUser(user.get());
            responseDto.setResult(exams);
            responseDto.setMessage("Exams found");
            responseDto.setWorked(true);
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("Couldn't find the user with the id  : " + id);
        return responseDto;

    }
    @Cacheable(cacheNames = "exams",key = "#id")

    public ResponseDto findExamById(Long id){
        var exam =repository.findById(id);
        if (exam.isPresent()){
            responseDto.setResult(exam.get());
            responseDto.setMessage("Found Exam successfully ");
            responseDto.setWorked(true);
            return  responseDto;

        } responseDto.setWorked(false);
        responseDto.setResult(null);

        responseDto.setMessage("exam doesn't exist with the id : " + id);
        return  responseDto;

    }

    @Deprecated
    public ResponseDto deleteExam(Long id){
        var exam =repository.findById(id);
        if (exam.isPresent()){
            repository.delete(exam.get());
            responseDto.setResult(exam.get());
            responseDto.setMessage("Deleted Exam successfully ");
            responseDto.setWorked(true);
            return  responseDto;

        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("exam doesn't exist with the id : " + id);
        return  responseDto;
    }


    public ResponseDto deleteAllExamsInExamGroup(){

        //TODO implement it
        return null;
    }

    @Cacheable(cacheNames = "exams",key = "#id")
    public ResponseDto getQuestionIdsByExamId(Long id){

        var exam = repository.findById(id);
        List<Long> ids = new ArrayList<>();
        if (exam.isPresent()){
            var questions = exam.get().getQuestions();
            for (Question question:questions){
                ids.add(question.getId());
            }
            responseDto.setResult(ids);
            responseDto.setMessage("Got iDs");
            responseDto.setWorked(true);
            return responseDto;
        }

        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("Exam doesn't exist");

        return responseDto;
    }
    public ResponseDto getUserExamsForProfile(Integer id,Integer page){

        int pageNumber = (page!=null && page> 0) ? page -1 :0;

        int pageSize =20;
        var user = userRepository.findById(id);
        if (user.isPresent()) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("passed").descending());
        }

        return responseDto;
    }


    public void correctExam(Long  examId){
        Optional<Exam> exam = repository.findById(examId);
        if (exam.isEmpty()){
            return;
        }

        int Sum = 0;
        for (Question question:exam.get().getQuestions()){
            if (question.getAnswer() == null){
                int correctOption = question.getTrueOption();
                if (question.getUserOption() == correctOption) Sum += question.getScore();

            }
            else {
                if (Objects.equals(question.getUserAnswer(), question.getAnswer())) Sum += question.getScore();
            }
        }
        if (exam.get().getPassingScore() <=Sum) {
            exam.get().setResult(true);
            repository.save(exam.get());
        }else {
            exam.get().setResult(false);
            repository.save(exam.get());
        }
    }

    public Integer GetPassingScore(Exam exam){
        int Sum = 0 ,i=0;
        for(Question question: exam.getQuestions()){
            Sum += question.getScore() ;i++;
        }
        Sum = (int )Sum/i;
        return Sum;
    }




    @Transactional
    public void DeleteAllUserWorkSpaceExams(Integer userId, Long workspaceId) throws Exception {
        var logger = LoggerFactory.getLogger(ExamService.class);

        var userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserDoesNotExistException("User doesn't exist");
        }

        var workspaceOptional = workspaceRepository.findById(workspaceId);
        if (workspaceOptional.isEmpty()) {
            throw new WorkSpaceDoesNotExistException("Workspace doesn't exist");
        }

        var user = userOptional.get();
        var workspace = workspaceOptional.get();

        // Process each exam in the workspace
        for (Exam exam : workspace.getExams()) {
            if (user.equals(exam.getStudent())) {
                var examGroup = exam.getExamGroup();
                var exams = examGroup.getExams().stream()
                        .filter(exam1 -> !exam.equals(exam1))
                        .collect(Collectors.toList());
                examGroup.setExams(exams);
                examGroupRepository.save(examGroup);  // Save after modifying exams
            }
        }

        // Fetch user exams related to the specific workspace and delete them
        var userExams = repository.findAllByStudentId(userId).stream()
                .filter(exam -> workspace.equals(exam.getWorkspace()))
                .collect(Collectors.toList());

        logger.info("User exams to be deleted: {}", userExams);

        repository.deleteAll(userExams);
    }



}
