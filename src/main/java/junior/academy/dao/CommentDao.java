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

    public List<Comment> getCommentsByUserId(long userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Comment where userId=:userId");
        query.setParameter("userId", userId);
        return query.list();
    }

    public List<Comment> getCommentsByUserNickname(String nickname) {
        Query query = sessionFactory.getCurrentSession().createQuery("select comment from Comment as comment join comment.user user where user.nickname=:nickname");
        query.setParameter("nickname", nickname);
        return query.list();
    }

    public List<Comment> getCommentsBySongId(long songId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Comment where songId=:songId");
        query.setParameter("songId", songId);
        return query.list();
    }

    public Optional <Comment> getCommentByUserIdAndSongId(long userId, long songId){
        Query query=sessionFactory.getCurrentSession().createQuery("from Comment where userId=:userId and songId=:songId");
        query.setParameter("userId",userId);
        query.setParameter("songId",songId);
        return Optional.ofNullable((Comment)query.uniqueResult());
    }
}