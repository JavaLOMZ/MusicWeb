package junior.academy.service;


import junior.academy.dao.CommentDao;
import junior.academy.dao.DefaultDao;
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

    @Mock
    DefaultDao defaultDao;

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
        doNothing().when(defaultDao).saveOrUpdate((any(Comment.class)));
        commentService.createOrUpdateComment(any(Comment.class));
        verify(defaultDao, atLeastOnce()).saveOrUpdate(any(Comment.class));
    }

    @Test
    public void getAllComments() {
        when(defaultDao.getAll(eq(Comment.class))).thenReturn(comments);
        assertEquals(commentService.getAllComments(), comments);
    }

    @Test
    public void deleteCommentById() {
        long id= comments.get(0).getCommentId();
        doNothing().when(defaultDao).deleteById(eq(Comment.class),anyLong());
        commentService.deleteCommentById(id);
        verify(defaultDao, atLeastOnce()).deleteById(eq(Comment.class),anyLong());
    }

    @Test
    public void getCommentById() {
        Comment comment = comments.get(0);
        when(defaultDao.getById(eq(Comment.class),anyLong())).thenReturn(Optional.ofNullable(comment));
        assertEquals(commentService.getCommentById(comment.getCommentId()), Optional.of(comment));
    }

    @Test
    public void isCommentPresent() {
        Comment comment = comments.get(0);
        when(defaultDao.getById(eq(Comment.class),anyLong())).thenReturn(Optional.ofNullable(comment));
        assertEquals(commentService.isCommentPresent(comment.getCommentId()), true);
    }

    @Test
    public void getCommentsByUserId(){
        when(commentDao.getCommentsByUserId(anyLong())).thenReturn(comments);
        assertEquals(commentService.getCommentsByUserId(anyLong()),comments);
    }

    @Test
    public void getCommentsByUserNickname(){
        when(commentDao.getCommentsByUserNickname(anyString())).thenReturn(comments);
        assertEquals(commentService.getCommentsByUserNickname(anyString()),comments);
    }

    @Test
    public void getCommentsBySongId(){
        when(commentDao.getCommentsBySongId(anyLong())).thenReturn(comments);
        assertEquals(commentService.getCommentsBySongId(anyLong()),comments);
    }

    @Test
    public void getCommentByUserIdAndSongId(){
        Comment comment = comments.get(0);
        when(commentDao.getCommentByUserIdAndSongId(anyLong(), anyLong())).thenReturn(Optional.ofNullable(comment));
        assertEquals(commentService.getCommentByUserIdAndSongId(anyLong(), anyLong()), Optional.ofNullable(comment));
    }

    private List<Comment> getCommentList() {
        Comment comment = new Comment();

        comments.add(comment);
        return comments;
    }
}
