package junior.academy.controller;

import junior.academy.domain.Comment;
import junior.academy.service.CommentService;
import junior.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable long commentId) {
        if (commentService.isCommentPresent(commentId)) {
            return new ResponseEntity<>(commentService.getCommentById(commentId).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUserId(@PathVariable long userId){
        if(userService.getUserById(userId).isPresent()){
            return new ResponseEntity<List<Comment>>(commentService.getCommentsByUserId(userId),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @PostMapping
    public void createOrUpdateComment(@RequestBody Comment comment) {
        commentService.createOrUpdateComment(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteCommentById(@PathVariable long commentId) {
        if (commentService.isCommentPresent(commentId)) {
            commentService.deleteCommentById(commentId);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
