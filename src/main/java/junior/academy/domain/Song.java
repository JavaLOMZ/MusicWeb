package junior.academy.domain;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "songId")
    private long songId;

    @Column(name = "songName")
    @NotNull
    private String songName;

    @Column(name="musicGenre")
    @Enumerated(EnumType.STRING)
    private MusicGenre musicGenre;

    @Column(name = "releaseYear")
    private int releaseYear;

    @Column(name = "youTubeLink")
    private String youTubeLink;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "authorId")
    @JsonUnwrapped
    @JsonIgnoreProperties({"songs"})
    private Author author;

    @OneToMany(mappedBy = "song", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"song", "user"})
    private Set<Rate> rates;

    @OneToMany(mappedBy = "song", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonIgnoreProperties({"song", "user"})
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


    public MusicGenre getMusicGenre() {
        return musicGenre;
    }

    public void setMusicGenre(MusicGenre musicGenre) {
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

    public Set<Rate> getRates() {
        return rates;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
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

    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", songName='" + songName + '\'' +
                ", musicGenre=" + musicGenre +
                ", releaseYear=" + releaseYear +
                ", youTubeLink='" + youTubeLink + '\'' +
                ", author=" + author +
                ", rates=" + rates +
                ", comments=" + comments +
                '}';
    }
}
