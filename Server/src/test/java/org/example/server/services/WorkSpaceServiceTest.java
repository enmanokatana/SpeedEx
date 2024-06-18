package org.example.server.services;

import org.example.server.Dtos.WorkSpaceDto;
import org.example.server.models.ResponseDto;
import org.example.server.models.User;
import org.example.server.models.Workspace;
import org.example.server.repositories.UserRepository;
import org.example.server.repositories.WorkspaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkSpaceServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @InjectMocks
    private WorkSpaceService workSpaceService;

    private User admin;
    private List<User> users;
    private WorkSpaceDto workSpaceDto;
    private User user;
    private Workspace workspace;
    private ResponseDto responseDto;

    @BeforeEach
    void setUp() {
        admin = new User();
        admin.setId(1);

        User user1 = new User();
        user1.setId(2);
        User user2 = new User();
        user2.setId(3);
        users = Arrays.asList(user1, user2);

        workSpaceDto = new WorkSpaceDto();
        workSpaceDto.setAdmin(1);
        workSpaceDto.setUsers(Arrays.asList(2, 3));
        workSpaceDto.setName("Workspace Name");
        workSpaceDto.setDescription("Workspace Description");
        user = new User();
        user.setId(1);
        user.setExams(new ArrayList<>());
        user.setWorkspaces(new ArrayList<>());

        workspace = new Workspace();
        workspace.setId(1L);
        workspace.setUsers(new ArrayList<>());
        workspace.setExams(new ArrayList<>());

        var responseDto = new ResponseDto();
    }

    @Test
    void createWorkspace_AdminExistsAndUsersExist() {
        when(userRepository.findById(1)).thenReturn(Optional.of(admin));
        when(userRepository.findAllById(Arrays.asList(2, 3))).thenReturn(users);
        when(workspaceRepository.save(any(Workspace.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseDto responseDto = workSpaceService.createWorkspace(workSpaceDto);

        assertTrue(responseDto.isWorked());
        assertEquals("Created Workspace Successfully", responseDto.getMessage());

        verify(userRepository).findById(1);
        verify(userRepository).findAllById(Arrays.asList(2, 3));
        verify(workspaceRepository).save(any(Workspace.class));
        verify(userRepository).save(admin);
        for (User user : users) {
            verify(userRepository).save(user);
        }
    }

    @Test
    void createWorkspace_AdminExistsAndUsersDoNotExist() {
        when(userRepository.findById(1)).thenReturn(Optional.of(admin));
        when(userRepository.findAllById(Arrays.asList(2, 3))).thenReturn(List.of());

        ResponseDto responseDto = workSpaceService.createWorkspace(workSpaceDto);

        assertTrue(responseDto.isWorked());
        assertEquals("Created Workspace Successfully", responseDto.getMessage());

        verify(userRepository).findById(1);
        verify(userRepository).findAllById(Arrays.asList(2, 3));
        verify(workspaceRepository).save(any(Workspace.class));
        verify(userRepository).save(admin);
    }


    @Test
    void createWorkspace_AdminDoesNotExist() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ResponseDto responseDto = workSpaceService.createWorkspace(workSpaceDto);

        assertFalse(responseDto.isWorked());
        assertEquals("Admin doesn't Exist", responseDto.getMessage());

        verify(userRepository).findById(1);
        verify(userRepository, never()).findAllById(any());
        verify(workspaceRepository, never()).save(any());
        verify(userRepository, never()).save(any(User.class));
    }
    @Test
    void addUserToWorkSpaceById_UserAndWorkspaceExist() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));

        ResponseDto response = workSpaceService.addUserToWorkSpaceById(1, 1L);

        assertTrue(response.isWorked());
        assertEquals("Added User Successfully to WorkSpace", response.getMessage());

        verify(userRepository).findById(1);
        verify(workspaceRepository).findById(1L);
        verify(userRepository).save(user);
        verify(workspaceRepository).save(workspace);
    }

    @Test
    void addUserToWorkSpaceById_UserAlreadyInWorkspace() {
        workspace.getUsers().add(user);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));

        ResponseDto response = workSpaceService.addUserToWorkSpaceById(1, 1L);

        assertFalse(response.isWorked());
        assertEquals("User Already Exist in WorkSpace", response.getMessage());

        verify(userRepository).findById(1);
        verify(workspaceRepository).findById(1L);
        verify(userRepository, never()).save(any());
        verify(workspaceRepository, never()).save(any());
    }

    @Test
    void addUserToWorkSpaceById_UserDoesNotExist() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ResponseDto response = workSpaceService.addUserToWorkSpaceById(1, 1L);

        assertFalse(response.isWorked());
        assertEquals("User doesn't exist", response.getMessage());

        verify(userRepository).findById(1);
        verify(workspaceRepository, never()).findById(any());
        verify(userRepository, never()).save(any());
        verify(workspaceRepository, never()).save(any());
    }

    @Test
    void addUserToWorkSpaceById_WorkspaceDoesNotExist() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(workspaceRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseDto response = workSpaceService.addUserToWorkSpaceById(1, 1L);

        assertFalse(response.isWorked());
        assertEquals("WorkSpace Doesn't Exist", response.getMessage());

        verify(userRepository).findById(1);
        verify(workspaceRepository).findById(1L);
        verify(userRepository, never()).save(any());
        verify(workspaceRepository, never()).save(any());
    }

    @Test
    @Disabled
    void addUserToWorkSpaceById_ExceptionThrown() {
        when(userRepository.findById(1)).thenThrow(new RuntimeException("Database Error"));

        ResponseDto response = workSpaceService.addUserToWorkSpaceById(1, 1L);

        assertFalse(response.isWorked());
        assertEquals("Database Error", response.getMessage());

        verify(userRepository).findById(1);
        verify(workspaceRepository, never()).findById(any());
        verify(userRepository, never()).save(any());
        verify(workspaceRepository, never()).save(any());
    }
}
