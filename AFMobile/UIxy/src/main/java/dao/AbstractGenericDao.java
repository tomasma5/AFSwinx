package dao;


import model.DtoEntity;

import java.util.List;

/**
 * @Author Pavel Matyáš <matyapav@fel.cvut.cz>.
 */
public interface AbstractGenericDao<T extends DtoEntity> {

    T getById(Integer id);

    void createOrUpdate(T dtoEntity);

    boolean delete(T dtoEntity);

    List<T> getAll();

    Long getAllCount(T dtoEntity);

}
