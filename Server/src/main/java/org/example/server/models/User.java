package org.example.server.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.server.AuthConfigurations.AuthEntites.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.server.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "myUser")

// Implementing UserDetails facilitate the update of the SecurityContextHolder in authentication.
public class User implements UserDetails, Serializable {


    @Serial
    private static final  long serialVersionUID = 8781617261L;
    @Id
    @GeneratedValue
    private Integer id;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    private String password;
    private String profileImg;

    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Token> tokens;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Exam> exams ; //User can create a lot of exams

    @OneToMany(mappedBy = "student" ,cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Exam> passingExams;// a user can have a bunch of exams to pass



    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Workspace> myWorkspaces;//owned

    @ManyToMany
    @JsonIgnore
    private List<Workspace> workspaces;//joined





    // Return User role
    //  authority in a String format.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    // getEmail Alias
    @Override
    public String getUsername() {
        return email;
    }

    // Default state of User
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}