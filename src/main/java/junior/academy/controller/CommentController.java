package junior.academy.controller;

import junior.academy.domain.Comment;
import junior.academy.service.CommentService;
import junior.academy.service.SongService;
import junior.academy.service.UserService;
import junior.academy.validator.CommentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    SongService songService;

    @Autowired
    CommentValidator commentValidator;

    @InitBinder()
    protected void initBinder(WebDataBinder binder){
        binder.setValidator(this.commentValidator);
    }

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

    @PostMapping
    public void createOrUpdateComment(@RequestBody @Valid Comment comment) {
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

    @GetMapping("user/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUserId(@PathVariable long userId){
        if(userService.isUserPresent(userId)){
            return new ResponseEntity<>(commentService.getCommentsByUserId(userId),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("user/nickname/{nickname}")
    public ResponseEntity<List<Comment>> getCommentsByUserNickname(@PathVariable String nickname){
        if(userService.getUserByUsername(nickname)!=null){
            return new ResponseEntity<>(commentService.getCommentsByUserNickname(nickname),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("song/{songId}")
    public ResponseEntity<List<Comment>> getCommentsBySongId(@PathVariable long songId){
        if(songService.isSongPresent(songId)){
            return new ResponseEntity<>(commentService.getCommentsBySongId(songId),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/{userId}/{songId}")
    public ResponseEntity<Comment> getCommentByUserIdAndSongId(@PathVariable long userId, @PathVariable long songId){
        Optional<Comment> commentResponse = commentService.getCommentByUserIdAndSongId(userId, songId);
        return commentResponse.map(comment -> new ResponseEntity<>(comment, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.OK));
    }
}
