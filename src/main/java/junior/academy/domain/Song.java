package junior.academy.domain;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "song")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="songId")
//@JsonIgnoreProperties({ "comments"})
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "songId")
    private long songId;

    @Column(name = "songName")
    @NotNull
    private String songName;

    @Column(name = "musicGenre")
    private String musicGenre;

    @Column(name = "releaseYear")
    private int releaseYear;

    @Column(name = "youTubeLink")
    private String youTubeLink;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authorId")
    @JsonUnwrapped
    private Author author;

    @OneToMany(mappedBy = "song", fetch = FetchType.EAGER)
    private Set<Rate> rate;

    @OneToMany(mappedBy = "song", fetch = FetchType.EAGER)
    private Set<Comment> comments;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getMusicGenre() {
        return musicGenre;
    }

    public void setMusicGenre(String musicGenre) {
        this.musicGenre = musicGenre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getYouTubeLink() {
        return youTubeLink;
    }

    public void setYouTubeLink(String youTubeLink) {
        this.youTubeLink = youTubeLink;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Set<Rate> getRate() {
        return rate;
    }

    public void setRate(Set<Rate> rate) {
        this.rate = rate;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }
}
