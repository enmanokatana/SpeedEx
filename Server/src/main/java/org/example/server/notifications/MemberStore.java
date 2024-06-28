package org.example.server.notifications;

import lombok.RequiredArgsConstructor;
import org.example.server.models.User;
import org.example.server.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberStore {


    private final OnlineUsersRepository repository;
    private final UserRepository userRepository;

    public void AddUser(Integer id){
        repository.save(OnlineUsers.builder()
                .OnlineUserId(id).build());
    }
    public void RemoveUser(Integer id){
        repository.deleteById(id);
    }
    public User getMemberifConnected(Integer id){
        if (repository.existsById(id)){
            return userRepository.findById(id).orElse(null);
        }

        return null;
    }
}
