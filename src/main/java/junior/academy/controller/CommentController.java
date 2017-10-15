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

    //@Autowired
    //SongService songService;

    @Autowired
    UserService userService;

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable long commentId) {
        if (commentService.isCommentPresent(commentId)) {
            return new ResponseEntity<>(commentService.getCommentById(commentId).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @PostMapping("/{userId}/{songId}")
    public ResponseEntity createOrUpdateComment(@RequestBody Comment comment, @PathVariable long userId, @PathVariable long songId) {
        if(userService.getUserById(userId).isPresent() /*&& songService.getSongById(long songId).isPresent()*/){
            comment.setUser(userService.getUserById(userId).get());
            //comment.setSong(songService.getSongById(songId).get());
            commentService.createOrUpdateComment(comment);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
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
