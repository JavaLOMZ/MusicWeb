package junior.academy.dao;

import junior.academy.domain.Comment;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CommentDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Optional<Comment> getCommentById(long commentId) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Comment.class, commentId));
    }

    public List<Comment> getAllComments() {
        return sessionFactory.getCurrentSession().createQuery("from Comment").list();
    }

    public void createOrUpdateComment(Comment comment) {
        sessionFactory.getCurrentSession().saveOrUpdate(comment);
    }

    public void deleteCommentById(long commentId) {
        sessionFactory.getCurrentSession().delete(getCommentById(commentId).get());
    }

    public List<Comment> getCommentsByUserId(long userId) {
        Query query=sessionFactory.getCurrentSession().createQuery("from Comment where userId=:userId");
        query.setParameter("userId",userId);
        return query.list();
    }

    public List<Comment> getCommentsBySongId(long songId){
        Query query=sessionFactory.getCurrentSession().createQuery("from Comment where songId=:songId");
        query.setParameter("songId",songId);
        return query.list();
    }

}