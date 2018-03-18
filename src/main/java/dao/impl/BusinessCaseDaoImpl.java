package dao.impl;

import dao.BusinessCaseDao;
import dao.ScreenDao;
import model.Screen;
import model.afclassification.BusinessCase;

import javax.enterprise.context.ApplicationScoped;

import static com.mongodb.client.model.Filters.eq;

/**
 * Implementation of Mongo DAO for business cases
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class BusinessCaseDaoImpl extends GenericMongoDaoImpl<BusinessCase> implements BusinessCaseDao {

    public BusinessCaseDaoImpl() {
    }

    @Override
    public Class getModelClass() {
        return BusinessCase.class;
    }

    @Override
    public String getCollectionName() {
        return "businessCases";
    }


}
