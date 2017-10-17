package junior.academy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    @NotNull
    private int rateValue; //min 1 max 10

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "songId")
    private Song song;

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
}
