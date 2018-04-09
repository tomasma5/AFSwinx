package dao.impl;

import dao.ScreenDao;
import model.Screen;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of DAO for application screens
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class ScreenDaoImpl extends AbstractGenericDaoImpl<Screen> implements ScreenDao {

    public ScreenDaoImpl() {
        super(Screen.class);
    }

    @Override
    public Screen findByKey(String key) {
        String query = Screen.SCREEN_KEY + " = :screenKey";
        Map<String, Object> params = new HashMap<>();
        params.put("screenKey", key);

        return getByWhereConditionSingleResult(query, params);
    }

    @Override
    public List<Screen> getScreensWithLoadedComponents() {
        try {
            Query query = getEntityManager().createQuery(
                    "SELECT DISTINCT s FROM Screen s join fetch s.components");
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Screen getScreenByIdWithLoadedComponents(Integer screenId) {
        try {
            Query query = getEntityManager().createQuery(
                    "SELECT DISTINCT s FROM Screen s left join fetch s.components where s.id = :screenId")
                    .setParameter("screenId", screenId);
            return (Screen) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
