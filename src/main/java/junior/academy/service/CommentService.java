package junior.academy.service;

import junior.academy.dao.CommentDao;
import junior.academy.dao.UserDao;
import junior.academy.domain.Comment;
import junior.academy.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    UserService userService;

    public Optional<Comment> getCommentById(long commentId){
        return commentDao.getCommentById(commentId);
    }

    public List<Comment> getAllComments(){
        return commentDao.getAllComments();
    }

    public void createOrUpdateComment(Comment comment){
        commentDao.createOrUpdateComment(comment);
    }

    public void deleteCommentById(long commentId){
        commentDao.deleteCommentById(commentId);
    }

    public boolean isCommentPresent(long commentId){
        return commentDao.getCommentById(commentId).isPresent();
    }

    public List<Comment> getCommentsByUserId(long userId){
        return commentDao.getCommentsByUserId(userId);
    }

    public List<Comment> getCommentsByUserNickname(String nickname){
        /*you've got here 2 options.
          commented one is using method which was wrote earlier
          the second one is using some hibernate new query schema, new for me so check it out*/

//        return commentDao.getCommentsByUserId(userService.findUserByName(nickname).getUserId());
        return commentDao.getCommentsByUserNickname(nickname);
    }

    public List<Comment> getCommentsBySongId(long songId){
        return commentDao.getCommentsBySongId(songId);
    }

    public Optional<Comment> getCommentForUserAndSong(long userId, long songId){
        return commentDao.getCommentForUserAndSong(userId,songId);
    }

}
