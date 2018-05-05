package dao.impl;


import dao.AbstractGenericDao;
import model.DtoEntity;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 */
@Stateless
public abstract class AbstractGenericDaoImpl<T extends DtoEntity> implements AbstractGenericDao<T> {

    @PersistenceContext(unitName = "UIxyPU")
    private EntityManager entityManager;

    private Class<T> clazz;

    public AbstractGenericDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T getById(Integer id) {
        return entityManager.find(clazz, id);
    }

    @Override
    public boolean delete(T entity) {
        try {
            entityManager.remove(entity);
            entityManager.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void createOrUpdate(T dtoEntity) {
        Integer id = dtoEntity.getId();
        if (id != null) {
            entityManager.merge(dtoEntity);
        } else {
            entityManager.persist(dtoEntity);
        }
        entityManager.flush();
    }

    @Override
    public List<T> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = criteriaBuilder.createQuery(clazz);
        Root<T> entity = cq.from(clazz);
        cq.select(entity);
        TypedQuery<T> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Long getAllCount(T dtoEntity) {
        Query query = getEntityManager().createQuery("select count(e) from " + dtoEntity.getClass().getName() + " e" );
        return (Long) query.getSingleResult();
    }

    protected Long getCountByCondition(String whereCondition, Map<String, Object> sqlParams) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(e) from " + clazz.getAnnotation(Table.class).name()).append(" e WHERE ");
        sb.append(whereCondition);
        Query query = getEntityManager().createQuery(sb.toString());

        for (Map.Entry<String, Object> entry : sqlParams.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return (Long) query.getSingleResult();
    }


    protected List<T> getByWhereCondition(String whereCondition, Map<String, Object> sqlParams) {
        try {
            return createGetWhereConditionQuery(whereCondition, sqlParams).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    protected T getByWhereConditionSingleResult(String whereCondition, Map<String, Object> sqlParams) {
        try {
            return (T) createGetWhereConditionQuery(whereCondition, sqlParams).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    protected Query createGetWhereConditionQuery(String whereCondition, Map<String, Object> sqlParams) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT e FROM ").append(clazz.getAnnotation(Table.class).name()).append(" e WHERE ");
        sb.append(whereCondition);
        Query query = entityManager.createQuery(sb.toString());
        for (Map.Entry<String, Object> entry : sqlParams.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }


}
