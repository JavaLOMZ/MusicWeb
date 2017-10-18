package junior.academy.controller;

import junior.academy.controller.CommentController;
import junior.academy.domain.Comment;
import junior.academy.service.CommentService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class CommentControllerTest {

    @Mock
    CommentService commentService;

    @InjectMocks
    CommentController commentController;

    @Spy
    List<Comment> comments = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        comments = getCommentList();
    }

    @Test
    public void getCommentWhenPresent(){
        Comment testComment = comments.get(0);
        when(commentService.isCommentPresent(anyLong())).thenReturn(true);
        when(commentService.getCommentById(anyLong())).thenReturn(java.util.Optional.ofNullable(testComment));
        assertEquals(commentController.getComment(anyLong()), new ResponseEntity<>(testComment, HttpStatus.OK));
    }

    @Test
    public void getCommentWhenNotPresent(){
        when(commentService.isCommentPresent(anyLong())).thenReturn(false);
        assertEquals(commentController.getComment(anyLong()), new ResponseEntity<>(any(Comment.class), HttpStatus.NOT_FOUND));
    }

    @Test
    public void getAllComments() {
        when(commentService.getAllComments()).thenReturn(comments);
        assertEquals(commentController.getAllComments(), comments);
    }

    @Test
    public void createOrUpdateComment(){
        doNothing().when(commentService).createOrUpdateComment((any(Comment.class)));
        commentController.createOrUpdateComment(any(Comment.class));
        verify(commentService, atLeastOnce()).createOrUpdateComment(any(Comment.class));
    }

    @Test
    public void deleteCommentWhenPresent() {
        when(commentService.isCommentPresent(anyLong())).thenReturn(true);
        doNothing().when(commentService).deleteCommentById(anyLong());
        assertEquals(commentController.deleteCommentById(anyLong()), new ResponseEntity(HttpStatus.OK));
        verify(commentService, atLeastOnce()).deleteCommentById(anyLong());
    }

    @Test
    public void deleteCommentWhenNotPresent() {
        when(commentService.isCommentPresent(anyLong())).thenReturn(false);
        assertEquals(commentController.deleteCommentById(anyLong()), new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    private List<Comment> getCommentList() {
        Comment comment = new Comment();

        comments.add(comment);
        return comments;
    }
}
