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

    //todo 4 tests missing

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
