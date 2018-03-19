package dao.impl;

import dao.BusinessCaseDao;
import dao.ScreenDao;
import model.Screen;
import model.afclassification.BCPhase;
import model.afclassification.BusinessCase;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;

import java.util.List;

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


    @Override
    public List<BCPhase> getPhases(ObjectId bcId) {
        BusinessCase businessCase = findById(bcId);
        if(businessCase != null){
            return businessCase.getPhases();
        }
        return null;
    }
}
