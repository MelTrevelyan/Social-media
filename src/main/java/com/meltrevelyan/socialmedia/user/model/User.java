package com.meltrevelyan.socialmedia.user.model;

import com.meltrevelyan.socialmedia.role.Role;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    @Column(name = "user_password")
    private String password;
    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "user_followers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> followers;
    @ToString.Exclude
    @ManyToMany(mappedBy = "followers")
    private Set<User> publishers;
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
}
