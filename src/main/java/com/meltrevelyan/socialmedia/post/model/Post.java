package com.meltrevelyan.socialmedia.post.model;

import com.meltrevelyan.socialmedia.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String heading;
    private String text;
    @Lob
    private byte[] image;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
