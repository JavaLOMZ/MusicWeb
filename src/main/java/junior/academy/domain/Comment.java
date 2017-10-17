package junior.academy.domain;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comment")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="commentId")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private long commentId;

    @Column(name = "commentText")
    @NotNull
    private String commentText;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    @JsonUnwrapped
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "songId")
    @JsonUnwrapped
    @JsonIgnoreProperties({"comments", "rate", "author"})
    private Song song;

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public long getCommentId() {
        return commentId;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
