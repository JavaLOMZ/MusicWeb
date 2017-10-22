package junior.academy.service;


import junior.academy.dao.CommentDao;
import junior.academy.domain.Comment;

import static org.junit.Assert.*;

import org.mockito.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class CommentServiceTest {
    @Mock
    CommentDao commentDao;

    @InjectMocks
    CommentService commentService;

    @Spy
    List<Comment> comments = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        comments = getCommentList();
    }

    @Test
    public void createOrUpdateComment() {
        doNothing().when(commentDao).createOrUpdateComment((any(Comment.class)));
        commentService.createOrUpdateComment(any(Comment.class));
        verify(commentDao, atLeastOnce()).createOrUpdateComment(any(Comment.class));
    }

    @Test
    public void getAllComments() {
        when(commentDao.getAllComments()).thenReturn(comments);
        assertEquals(commentService.getAllComments(), comments);
    }

    @Test
    public void deleteCommentById() {
        doNothing().when(commentDao).deleteCommentById(anyLong());
        commentService.deleteCommentById(anyLong());
        verify(commentDao, atLeastOnce()).deleteCommentById(anyLong());
    }

    @Test
    public void getCommentById() {
        Comment comment = comments.get(0);
        when(commentDao.getCommentById(anyLong())).thenReturn(Optional.ofNullable(comment));
        assertEquals(commentService.getCommentById(anyLong()), Optional.of(comment));
    }

    @Test
    public void getCommentsByUserId(){
        when(commentDao.getCommentsByUserId(anyLong())).thenReturn(comments);
        assertEquals(commentService.getCommentsByUserId(anyLong()),comments);
    }

    private List<Comment> getCommentList() {
        Comment comment = new Comment();

        comments.add(comment);
        return comments;
    }
}
