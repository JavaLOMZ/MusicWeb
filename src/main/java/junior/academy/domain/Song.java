package junior.academy.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "songId")
    private long songId;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private Author author;


    @Column(name = "songName")
    @NotNull
    private String songName;

    @Column(name = "musicGenre")
    private String musicGenre;

    @Column(name = "releaseYear")
    private int releaseYear;

    @Column(name = "youTubeLink")
    private String youTubeLink;

    @OneToMany(mappedBy = "song")
    private List<Rate> rate;

    @OneToMany(mappedBy = "song")
    private List<Comment> comments;

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

    public List<Rate> getRate() {
        return rate;
    }

    public void setRate(List<Rate> rate) {
        this.rate = rate;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


}
