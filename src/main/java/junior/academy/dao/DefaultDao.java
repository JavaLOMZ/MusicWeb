package junior.academy.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class DefaultDao {

    @Autowired
    private SessionFactory sessionFactory;

    public <T>Optional<T> getById(final Class<T> type, final Long id){
        return Optional.ofNullable((T) sessionFactory.getCurrentSession().get(type,id));
    }

    public <T> List<T> getAll(final Class<T>type){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(type);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public <T> void deleteById(final Class<T> type,final Long id){
        sessionFactory.getCurrentSession().delete(getById(type,id).get());
    }

    public <T> void saveOrUpdate(final T instance){
        sessionFactory.getCurrentSession().saveOrUpdate(instance);
    }

}
