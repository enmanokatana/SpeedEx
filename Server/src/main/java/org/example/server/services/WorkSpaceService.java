package org.example.server.services;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.ExamDto;
import org.example.server.Dtos.UserDto;
import org.example.server.Dtos.WorkSpaceDto;
import org.example.server.enums.Result;
import org.example.server.models.Exam;
import org.example.server.models.ResponseDto;
import org.example.server.models.User;
import org.example.server.models.Workspace;
import org.example.server.notifications.NotificationController;
import org.example.server.repositories.ExamRepository;
import org.example.server.repositories.UserRepository;
import org.example.server.repositories.WorkspaceRepository;
import org.springframework.stereotype.Service;

import java.util.*;






@Service
@RequiredArgsConstructor
public class WorkSpaceService {

    private final NotificationController notificationController;
    private final WorkspaceRepository repository;
    private final UserRepository userRepository;
    private final ExamService examService;
    private final ExamRepository examRepository;
    private final ResponseDto responseDto = new ResponseDto();
    private final UserService userService;

    public ResponseDto getWorkSpaceAdmin(Long id){
        var ws = repository.findById(id);
        if (ws.isPresent()){
            var user = ws.get().getAdmin();
            var admin = UserDto.builder()
                    .email(user.getEmail())
                    .role(user.getRole())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .profileImg(user.getProfileImg())
                    .build();
            responseDto.setResult(admin);
            responseDto.setMessage("Found Ws Admin ");
            responseDto.setWorked(true);
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("Couldn't find this workspace");
        return responseDto;
    }
    public ResponseDto getWorkSpaceById(Long id){
        var ws = repository.findById(id);
        if (ws.isPresent()){
            responseDto.setResult(ws.get().getAdmin());
            responseDto.setMessage("Found Ws Admin ");
            responseDto.setWorked(true);
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("Couldn't find this workspace");
        return responseDto;

    }

    public ResponseDto addUserToWorkSpaceById(Integer id , Long Wid){
        var user = userRepository.findById(id);
        if (user.isEmpty()){
            responseDto.setWorked(false);
            responseDto.setResult(null);
            responseDto.setMessage("User doesn't exist");

            return responseDto;
        }
        var workspace = repository.findById(Wid);
        if (workspace.isEmpty()){
            responseDto.setWorked(false);
            responseDto.setResult(null);
            responseDto.setMessage("WorkSpace Doesn't Exist");
            return responseDto;
        }
        if (workspace.get().getUsers().contains(user.get())){
            responseDto.setWorked(false);
            responseDto.setResult(null);
            responseDto.setMessage("User Already Exist in WorkSpace");
            return responseDto;
        }
        //////////////////////////////////////////
        List<Exam> exams = new ArrayList<>();
        List<Exam> examsToAdd = new ArrayList<>();
        for (Exam exam:workspace.get().getExams())
        {
            if (exam.getStudent() == null){
                examsToAdd.add(exam);
            }
        }

        for (Exam examDto:examsToAdd){

            exams.add( (Exam)
                    examService.createExamNew(ExamDto.builder()
                                    .id(0L)
                            .isPublic(examDto.isPublic())
                            .questions(examDto.getQuestions())
                            .student(user.get().getId())
                            .workspace(examDto.getWorkspace().getId())
                            .user(examDto.getUser().getId())
                            .passingScore(examDto.getPassingScore())
                            .timer(examDto.getTimer())
                            .randomizeQuestions(examDto.isRandomizeQuestions())
                            .name(examDto.getName())
                            .description(examDto.getDescription())
                            .difficultyLevel(examDto.getDifficultyLevel())
                                    .passingDate(examDto.getPassingDate())
                                    .passed(false)
                                    .ExamGroup(examDto.getExamGroup().getId())
                                    .result(Result.EMPTY)


                            .build()).getResult());

        }



        user.get().getExams().addAll(exams);
        user.get().getWorkspaces().add(workspace.get());//assigning the user the exams before adding him there ;
        userRepository.save(user.get());
        //////////////////////////////////////
        workspace.get().getUsers().add(user.get());
        repository.save(workspace.get());
        responseDto.setWorked(true);
        responseDto.setMessage("Added User Successfully to WorkSpace");
        responseDto.setResult(null);
        return responseDto;

    }
    @Deprecated
    public ResponseDto addUserToWorkSpace(Integer id , Long Wid) {
        try{
            var user = userRepository.findById(id);
            var workspace = repository.findById(Wid);
            if (user.isPresent()){
                if (workspace.isPresent()){
                    if (workspace.get().getUsers().contains(user.get())){
                        responseDto.setWorked(false);
                        responseDto.setResult(null);
                        responseDto.setMessage("User Already Exist in WorkSpace");
                        return responseDto;
                    }
                    //////////////////////////////////////////
                    List<Exam> exams = new ArrayList<>();
                    List<Exam> examsToAdd = new ArrayList<>();
                    for (Exam exam:workspace.get().getExams())
                    {
                        if (exam.getStudent() == null){
                            examsToAdd.add(exam);
                        }
                    }

                    for (Exam examDto:examsToAdd){

                        exams.add( (Exam)
                                examService.createExamNew(ExamDto.builder()
                                        .isPublic(examDto.isPublic())
                                        .questions(examDto.getQuestions())
                                        .student(user.get().getId())
                                        .workspace(examDto.getWorkspace().getId())
                                        .user(examDto.getUser().getId())
                                        .passingScore(examDto.getPassingScore())
                                        .timer(examDto.getTimer())
                                        .randomizeQuestions(examDto.isRandomizeQuestions())
                                        .name(examDto.getName())
                                        .description(examDto.getDescription())
                                        .difficultyLevel(examDto.getDifficultyLevel())
                                        .build()).getResult());

                    }


                    user.get().getExams().addAll(exams);
                    user.get().getWorkspaces().add(workspace.get());//assigning the user the exams before adding him there ;
                    userRepository.save(user.get());
                    //////////////////////////////////////
                    workspace.get().getUsers().add(user.get());
                    repository.save(workspace.get());
                    responseDto.setWorked(true);
                    responseDto.setMessage("Added User Successfully to WorkSpace");
                    responseDto.setResult(null);
                    return responseDto;
                }
                responseDto.setWorked(false);
                responseDto.setResult(null);
                responseDto.setMessage("WorkSpace Doesn't Exist");
                return responseDto;
            }
            responseDto.setWorked(false);
            responseDto.setResult(null);
            responseDto.setMessage("User doesn't exist");

            return responseDto;
        }catch(Exception e){
            responseDto.setMessage(e.getMessage());
            responseDto.setWorked(false);
            return responseDto;

        }


    }

    public ResponseDto addUserToWorkspaceByEmail(String email ,Long Wid ){
        var user = userRepository.findByEmail(email);
        var workspace = repository.findById(Wid);
        if (user.isPresent()){
            if (workspace.isPresent()){
                if (workspace.get().getUsers().contains(user.get())){
                    responseDto.setWorked(false);
                    responseDto.setResult(null);
                    responseDto.setMessage("User Already Exist in WorkSpace");
                    return responseDto;
                }

                //We Are creating a copy of the exams for the user
                ///////////////////////////////////////////
                List<Exam> exams = new ArrayList<>();

                for (Exam examDto:workspace.get().getExams()){

                   exams.add( (Exam)
                    examService.createExamNew(ExamDto.builder()
                               .isPublic(examDto.isPublic())
                               .questions(examDto.getQuestions())
                               .student(user.get().getId())
                               .workspace(examDto.getWorkspace().getId())
                               .user(examDto.getUser().getId())
                               .passingScore(examDto.getPassingScore())
                               .timer(examDto.getTimer())
                               .randomizeQuestions(examDto.isRandomizeQuestions())
                               .name(examDto.getName())
                               .description(examDto.getDescription())
                               .difficultyLevel(examDto.getDifficultyLevel())
                       .build()).getResult());

                }


                user.get().setPassingExams(exams);//assigning the user the exams before adding him there ;
                userRepository.save(user.get());
                ///////////////////////////////

                workspace.get().getUsers().add(user.get());
                repository.save(workspace.get());
                responseDto.setWorked(true);
                responseDto.setMessage("Added User Successfully to WorkSpace");
                responseDto.setResult(null);
                return responseDto;
            }
            responseDto.setWorked(false);
            responseDto.setResult(null);
            responseDto.setMessage("WorkSpace Doesn't Exist");
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("User doesn't exist");

        return responseDto;
    }



    public ResponseDto createWorkspace(WorkSpaceDto workSpaceDto){

        Optional<User> admin = userRepository.findById(workSpaceDto.getAdmin());

        if (admin.isPresent()){

            var users = userRepository.findAllById(workSpaceDto.getUsers());

            if(users.isEmpty()){
                users = null;
            }
            var result = repository.save(Workspace.builder()
                    .admin(admin.get())
                    .name(workSpaceDto.getName())
                    .id(0L)
                    .description(workSpaceDto.getDescription())
                    .exams(null)
                    .image("https://firebasestorage.googleapis.com/v0/b/library-b3d3f.appspot.com/o/d76d08b9-afe5-44d5-84f2-518a3b9c60ed.jpeg?alt=media")
                    .users(users)
                    .code(generateUniqueWorkspaceCode())
                    .build());
            responseDto.setResult(result);

            var adminsWorkspaces = admin.get().getMyWorkspaces();
            if (adminsWorkspaces == null)
            {
                adminsWorkspaces = new ArrayList<>();
                adminsWorkspaces.add(result);
            }else
            {
                adminsWorkspaces.add(result);
            }
            admin.get().setMyWorkspaces(adminsWorkspaces);
            userRepository.save(admin.get());

            if (users!=null){
                for (User user:users){
                    var usersWorkspaces = user.getWorkspaces();
                    if (usersWorkspaces == null){
                        usersWorkspaces = new ArrayList<>();
                    }else {
                        usersWorkspaces.add(result);
                    }
                    user.setWorkspaces(usersWorkspaces);
                    userRepository.save(user);
                }


            }
            responseDto.setWorked(true);
            responseDto.setMessage("Created Workspace Successfully");
            return  responseDto;

        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("Admin doesn't Exist");
        return responseDto;


    }



    public ResponseDto getWorkspaceByAdminId(Integer id){
        var admin = userRepository.findById(id);
        if (admin.isPresent()){
            var workspaces = repository.findByAdmin(admin.get());
            responseDto.setResult(workspaces);
            responseDto.setMessage("Found Wspaces");
            responseDto.setWorked(true);
            return responseDto;

        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("this admin doesn't exist");
        return responseDto;

    }

    public ResponseDto getWorkSpaceDto(Long id){
        var workspaceO = repository.findById(id);
        if (workspaceO.isPresent()){
            var workspace = workspaceO.get();
            var result = WorkSpaceDto.builder()
                    .name(workspace.getName())
                    .description(workspace.getDescription())
                    .id(workspace.getId())
                    .image(workspace.getImage())
                    .code(workspace.getCode())
                    .build();

            responseDto.setResult(result);
            responseDto.setWorked(true);
            responseDto.setMessage("Found Ws Dto");
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("Workspace doesn't Exist");
        return  responseDto;




    }


    public ResponseDto getUsersFromWorkSpace(Long id){

        var workspace = repository.findById(id);

        if (workspace.isPresent()){
            var users = workspace.get().getUsers();
            List<Integer> userIds = new ArrayList<>();
            for (User user:users){
                userIds.add(user.getId());
            }
            responseDto.setResult(userIds);
            responseDto.setMessage("FoundUsers that belong to this workspace");
            responseDto.setWorked(true);
            return responseDto;

        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("Workspace doesn't Exist");
        return  responseDto;



    }

    //returns all the exams for any workspace
    public ResponseDto GetWorkSpaceExams(Long id){
        var workspace = repository.findById(id);
        List<ExamDto> exams = new ArrayList<>();
        if (workspace.isPresent()){
            for (Exam exam : workspace.get().getExams()){
                exams.add(ExamDto.builder()
                                .name(exam.getName())
                                .timer(exam.getTimer())
                                .difficultyLevel(exam.getDifficultyLevel())
                                .description(exam.getDescription())
                                .student(exam.getStudent()== null ? 0 : exam.getStudent().getId())
                                .user(exam.getUser().getId())
                                .id(exam.getId())
                                .passed(exam.getPassed())
                                .passingScore(0)
                                .ExamGroup(exam.getExamGroup().getId())
                        .build());
            }
            responseDto.setResult(exams);;
            responseDto.setMessage("exams found ");
            responseDto.setWorked(true);
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("Workspace doesn't exist");
        return responseDto;
    }
    //returns exams for a User inside a workspace
    public ResponseDto GetWorkSpaceExamsForAUser(Long id,Integer userId){
        var workspace = repository.findById(id);
        List<ExamDto> exams = new ArrayList<>();
        if (workspace.isPresent()){
            for (Exam exam : workspace.get().getExams()){
                exams.add(ExamDto.builder()
                                .name(exam.getName())
                                .timer(exam.getTimer())
                                .difficultyLevel(exam.getDifficultyLevel())
                                .description(exam.getDescription())
                                .student(exam.getStudent()== null ? 0 : exam.getStudent().getId())
                                .user(exam.getUser().getId())
                                .id(exam.getId())
                                .passed(exam.getPassed())
                                .result(exam.getResult())
                                .createdOn(exam.getCreatedOn())
                                .passingScore(0)
                        .build());
            }
            exams = exams.stream().filter(examDto -> Objects.equals(examDto.getStudent(), userId)).toList();
            responseDto.setResult(exams);;
            responseDto.setMessage("exams found ");
            responseDto.setWorked(true);
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("Workspace doesn't exist");
        return responseDto;
    }


    // just basic infos about exams for the admin to acess all the other exams

    // Make sure to check that it is the right admin just
    public ResponseDto GetWorkSpaceExamsForAnAdmin(Long id,Integer userId){
        var workspace = repository.findById(id);
        List<ExamDto> exams = new ArrayList<>();
        if (workspace.isPresent()){
            for (Exam exam : workspace.get().getExams()){
                if (exam.getStudent() == null){
                    exams.add(ExamDto.builder()
                                    .name(exam.getName())
                                    .timer(exam.getTimer())
                                    .difficultyLevel(exam.getDifficultyLevel())
                                    .description(exam.getDescription())
                                    .user(exam.getUser().getId())
                                    .id(exam.getId())
                                    .createdOn(exam.getCreatedOn())
                                    .passed(exam.getPassed())
                                    .passingScore(0)
                                    .ExamGroup(exam.getExamGroup().getId())
                            .build());
                }
            }
            responseDto.setResult(exams);;
            responseDto.setMessage("exams found ");
            responseDto.setWorked(true);
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("Workspace doesn't exist");
        return responseDto;
    }

    // Old
    public ResponseDto RemoveUserFromWorkSpace(Integer userId,Long Wid){
        try{
            var user =   userRepository.findById(userId);
            var workspace = repository.findById(Wid);
            if (workspace.isPresent()) {
                if (user.isPresent()) {
                    //////////////////////////////////////////////////////////
                   // examService.DeleteAllUserWorkSpaceExams(userId,Wid);
                    ///////////////////////////////////////////////
                    user.get().getWorkspaces().remove(workspace.get());
                    userRepository.save(user.get());//Remove Ws From user Entity
                    //////////////////////////////////////////////

                    workspace.get().getUsers().remove(user.get()); // Remove the user from the workspace
                    repository.save(workspace.get());
                    /////////////////////////////:

                     var WSxexams = examRepository.findAllByStudentId(userId).stream().filter(exam -> exam.getWorkspace().getId() == Wid ).toList();
                     examRepository.deleteAll(WSxexams);//removig the users Exams
                    responseDto.setMessage("removed user with success ");
                    responseDto.setWorked(true);
                    responseDto.setResult(null);
                    return responseDto;
                }

                responseDto.setMessage("User doesn't Exist ");
                responseDto.setWorked(false);
                responseDto.setResult(null);
                return responseDto;
            }

            responseDto.setMessage("WorkSpace doesn't Exist ");
            responseDto.setWorked(false);
            responseDto.setResult(null);
            return responseDto;

        }catch(Exception e){
            responseDto.setWorked(false);
            responseDto.setResult(null);
            responseDto.setMessage(e.getMessage());
            return responseDto;
        }
    }





    public ResponseDto getWorkSpaceImage(Long id){
        var ws = repository.findById(id);

        if (ws.isPresent()){
            responseDto.setResult(ws.get().getImage());
            responseDto.setWorked(true);
            responseDto.setMessage("GotImage");
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        return responseDto;
    }

    public ResponseDto getWorkSpaceName(Long id){
        var ws = repository.findById(id);

        if (ws.isPresent()){
            responseDto.setResult(ws.get().getName());
            responseDto.setWorked(true);
            responseDto.setMessage("GotName");
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        return responseDto;
    }


    public ResponseDto joinUserToWorkSpaceByCode(Integer id, String code){
        var workspaceOptional = repository.findByCode(code);
        if (workspaceOptional.isEmpty()){
            responseDto.setWorked(false);
            responseDto.setResult(null);
            responseDto.setMessage("Wrong Code");
            return responseDto;
        }
        var userOptional =userRepository.findById(id);
        if (userOptional.isEmpty()){
            responseDto.setWorked(false);
            responseDto.setResult(null);
            responseDto.setMessage("User doesn't exist");
            return responseDto;
        }

        return addUserToWorkSpaceById(id,workspaceOptional.get().getId());
    }

    public String generateUniqueWorkspaceCode(){
        String code;
        do {
            code = generateWorkSpaceCode();
        }while (repository.existsByCode(code));
        return code;
    }
    private String generateWorkSpaceCode(){
        return UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }

}
