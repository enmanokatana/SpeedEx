package org.example.server.notifications;

import org.example.server.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineUsersRepository extends  JpaRepository<OnlineUsers, Integer> {

}
