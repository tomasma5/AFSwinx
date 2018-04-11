package dao;

import model.afclassification.BCPhase;

import java.util.List;

/**
 * DAO for application screens
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface BusinessPhaseDao extends AbstractGenericDao<BCPhase> {

    //no need to implement other dao
    public List<BCPhase> getBusinessPhasesWithLoadedScreens();

    public BCPhase getBusinessPhaseByIdWithLoadedScreens(Integer bpId);
}
