package junior.academy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comment")
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
    @JsonBackReference("a")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "songId")
    @JsonBackReference("c")
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
