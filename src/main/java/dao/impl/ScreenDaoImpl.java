package dao.impl;

import dao.ScreenDao;
import model.Screen;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
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
}
