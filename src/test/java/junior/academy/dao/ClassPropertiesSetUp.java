package junior.academy.dao;

import junior.academy.domain.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClassPropertiesSetUp {

    static final String TEST_COMMENT_TEXT = "TestCommentText";
    static final String TEST_USER_NICKNAME ="TestUserNickname";
    static final String TEST_USER_PASSWORD = "TestUserPassword";
    static final String TEST_USER_EMAIL = "TestUserEmail";
    static final String TEST_AUTHOR_NAME = "TestAuthorName";
    static final String TEST_AUTHOR_SEARCH_WORD = TEST_AUTHOR_NAME.substring(2, 5);
    static final int TEST_AUTHOR_YOB = (int) (Math.random()*2000);
    static final String TEST_AUTHOR_COUNTRY = "TestAuthorCountry";
    static final String TEST_SONG_NAME = "TestSongName";
    static final String TEST_SONG_SEARCH_WORD = TEST_SONG_NAME.substring(2, 5);
    static final String TEST_SONG_SEARCH_WORD_NOT_FINDABLE = "NotFindableSearchWord";
    static final String TEST_YOUTUBE_LINK = "TestYoutubeLink";
    static final int TEST_REALEASE_YEAR = (int) (Math.random()*2000);
    static final int TEST_RATE_VALUE = (int) (Math.random()*10);

    Comment comment;
    User user;
    Song song;
    Author author;
    Rate rate;

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    DefaultDao defaultDao;

    void userSetUp(){
        user = new User();
        user.setNickname(TEST_USER_NICKNAME);
        user.setPassword(TEST_USER_PASSWORD);
        user.setEmail(TEST_USER_EMAIL);
        user.setEnabled(true);
    }

    void authorSetUp(){
        author = new Author();
        author.setName(TEST_AUTHOR_NAME);
        author.setCountryOfOrigin(TEST_AUTHOR_COUNTRY);
        author.setYearOfBirth(TEST_AUTHOR_YOB);
    }

    Song songSetUp(){
        Song song = new Song();
        song.setSongName(TEST_SONG_NAME);
        song.setMusicGenre(MusicGenre.HIPHOP);
        song.setYouTubeLink(TEST_YOUTUBE_LINK);
        song.setReleaseYear(TEST_REALEASE_YEAR);
        return song;
    }

    void rateSetUp(){
        rate = new Rate();
        rate.setRateValue(TEST_RATE_VALUE);
    }

    void commentSetUp(){
        comment = new Comment();
        comment.setCommentText(TEST_COMMENT_TEXT);
    }

    void flushCurrentSession() {
        sessionFactory.getCurrentSession().flush();
    }
}
