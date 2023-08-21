package com.meltrevelyan.socialmedia.friendship.model;

import com.meltrevelyan.socialmedia.user.model.User;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friendships")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;
}
