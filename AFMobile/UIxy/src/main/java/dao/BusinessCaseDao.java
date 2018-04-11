package dao;

import model.afclassification.BusinessCase;
import java.util.List;

/**
 * Mongo DAO for application screens
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface BusinessCaseDao extends AbstractGenericDao<BusinessCase> {

    //no need to implement other dao

    public List<BusinessCase> getBusinessCasesWithLoadedPhases();

}
