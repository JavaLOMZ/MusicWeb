package junior.academy.service;

import junior.academy.dao.CommentDao;
import junior.academy.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

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

    public List<Comment> getCommentsBySongId(long songId){
        return commentDao.getCommentsBySongId(songId);
    }
}
