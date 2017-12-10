package junior.academy.dao;

import junior.academy.domain.Comment;
import junior.academy.domain.Song;
import junior.academy.domain.User;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Matchers.anyLong;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CommentDaoTest extends EntityDaoTest {

    @Autowired
    CommentDao commentDao;

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet[] datasets = new IDataSet[]{
                new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Comment.xml"))
        };
        return new CompositeDataSet(datasets);
    }

    @Test
    public void getAllComments() {
        assertEquals(commentDao.getAllComments().size(), 1);
    }

    @Test
    public void getCommentById() {
        assertNotNull(commentDao.getCommentById(1));
        assertTrue(commentDao.getCommentById(1).get().getCommentText().equals("coolsong"));
    }

    @Test
    public void createComment() {
        Comment comment = getComment();
        commentDao.createOrUpdateComment(comment);
        assertEquals(commentDao.getAllComments().size(), 2);
    }

    @Test
    public void updateComment() {
        Comment commentTest = commentDao.getCommentById(1).get();
        commentTest.setCommentText("badsong");
        commentDao.createOrUpdateComment(commentTest);
        assertTrue(commentDao.getCommentById(1).get().getCommentText().equals("badsong"));
    }

    @Test
    public void deleteComment() {
        commentDao.deleteCommentById(1);
        assertEquals(commentDao.getAllComments().size(), 0);
    }

    @Test
    public void getCommentsByUserId(){
        assertEquals(commentDao.getCommentsByUserId(1).size(),1);
    }


    @Test
    public void getCommentsBySongId(){
        assertEquals(commentDao.getCommentsBySongId(1).size(),1);
    }

    @Test
    public void getCommentsByUserNickname(){
       //todo
    }

    @Test
    public void getCommentByUserIdAndSongId(){
        //todo
    }

    private Comment getComment() {
        Comment comment = new Comment();
        comment.setCommentText(" ");
        return comment;
    }


}
