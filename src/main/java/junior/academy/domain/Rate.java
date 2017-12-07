package junior.academy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rate")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rateId")
    private long rateId;

    @Column(name = "rateValue")
    private int rateValue; //min 1 max 10

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    @JsonUnwrapped
    @JsonIgnoreProperties({ "comments", "rates"})
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "songId")
    @JsonUnwrapped
    @JsonIgnoreProperties({"comments", "rates", "author"})
    private Song song;

    public Rate( User user, Song song) {
        this.user = user;
        this.song = song;
    }

    public Rate() {}

    public int getRateValue() {
        return rateValue;
    }

    public void setRateValue(int rateValue) {
        this.rateValue = rateValue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public long getRateId() {
        return rateId;
    }

    public void setRateId(long rateId) {
        this.rateId = rateId;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "rateId=" + rateId +
                ", rateValue=" + rateValue +
                ", user=" + user +
                ", song=" + song +
                '}';
    }
}
