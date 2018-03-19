package dao;

import model.afclassification.BCPhase;
import model.afclassification.BusinessCase;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Mongo DAO for application screens
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface BusinessCaseDao extends GenericMongoDao<BusinessCase> {

    //no need to implement other dao

    public List<BCPhase> getPhases(ObjectId bcId);
}
