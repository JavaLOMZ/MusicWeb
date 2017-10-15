package junior.academy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    @NotNull
    private String name;

    @Column(name = "yearOfBirth")
    private int yearOfBirth;

    @Column(name = "countryOfOrigin")
    private String countryOfOrigin;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Song> songs;

    public long getAuthorId() {
        return authorId;
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
}
