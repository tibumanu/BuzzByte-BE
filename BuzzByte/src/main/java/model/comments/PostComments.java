package model.comments;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import model.Posts;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class PostComments extends Comments {
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "postId", nullable = false)
    private Posts post;
}
