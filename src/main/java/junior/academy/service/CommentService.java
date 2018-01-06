package junior.academy.service;

import junior.academy.dao.CommentDao;
import junior.academy.dao.DefaultDao;
import junior.academy.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    DefaultDao defaultDao;

    @Autowired
    UserService userService;

    public Optional<Comment> getCommentById(long commentId){
        return defaultDao.getById(Comment.class,commentId);
    }

    public List<Comment> getAllComments(){
        return defaultDao.getAll(Comment.class);
    }

    public void createOrUpdateComment(Comment comment){
        defaultDao.saveOrUpdate(comment);
    }

    public void deleteCommentById(long commentId){
        defaultDao.deleteById(Comment.class,commentId);
    }

    public boolean isCommentPresent(long commentId){
        return defaultDao.getById(Comment.class,commentId).isPresent();
    }

    public List<Comment> getCommentsByUserId(long userId){
        return commentDao.getCommentsByUserId(userId);
    }

    public List<Comment> getCommentsByUserNickname(String nickname){
        return commentDao.getCommentsByUserNickname(nickname);
    }

    public List<Comment> getCommentsBySongId(long songId){
        return commentDao.getCommentsBySongId(songId);
    }

    public Optional<Comment> getCommentByUserIdAndSongId(long userId, long songId){
        return commentDao.getCommentByUserIdAndSongId(userId,songId);
    }

}
