package org.example.server.repositories.Auth;

import  org.example.server.AuthConfigurations.AuthEntites.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query("""
      select t from Token t  join User u on t.user.id = u.id
      where u.id = :id and t.revoked = false""")
  List<Token> findAllValidTokenByUser(Integer id);

  Optional<Token> findByToken(String token);
}
