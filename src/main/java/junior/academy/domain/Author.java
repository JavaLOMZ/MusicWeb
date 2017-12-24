package junior.academy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "author")

public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authorId")
    private long authorId;

    @Column(name = "name")
    private String name;

    @Column(name = "yearOfBirth")
    private int yearOfBirth;

    @Column(name = "countryOfOrigin")
    private String countryOfOrigin;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Set<Song> songs;

    public Author(String name, int yearOfBirth, String countryOfOrigin) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.countryOfOrigin = countryOfOrigin;
    }

    public Author() {}

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                '}';
    }
}