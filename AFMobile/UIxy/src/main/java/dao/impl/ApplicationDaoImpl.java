package dao.impl;

import dao.ApplicationDao;
import model.Application;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of DAO for applications
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class ApplicationDaoImpl extends AbstractGenericDaoImpl<Application> implements ApplicationDao {

    public ApplicationDaoImpl() {
        super(Application.class);
    }

    @Override
    public Application findByName(String name) {
        String query = Application.APPLICATION_NAME + " = :applicationName";
        Map<String, Object> params = new HashMap<>();
        params.put("applicationName", name);

        return getByWhereConditionSingleResult(query, params);
    }

    @Override
    public Application findByUuid(String uuid) {
        String query = Application.APPLICATION_UUID + " = :applicationUuid";
        Map<String, Object> params = new HashMap<>();
        params.put("applicationUuid", uuid);

        return getByWhereConditionSingleResult(query, params);
    }
}
