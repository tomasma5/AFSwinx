package service.afclassification.computational;

import model.afclassification.BCPhase;
import model.afclassification.SupportedClassificationUnit;
import model.afclassification.SupportedScoringUnit;
import service.afclassification.computational.ccm.BaseClassificationUnit;
import service.afclassification.computational.scm.BaseScoringUnit;

/**
 * The type Af classification factory.
 */
public class AFClassificationFactory {

    private static AFClassificationFactory instance = null;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized AFClassificationFactory getInstance() {
        if (instance == null) {
            instance = new AFClassificationFactory();
        }
        return instance;
    }

    /**
     * Gets classification module by phase.
     *
     * @param phase the phase
     * @return the classification module
     */
    public AFClassification getClassificationModule(BCPhase phase) {
        return getClassificationModule(phase.getClassificationUnit(), phase.getScoringUnit());
    }

    /**
     * Gets classification module by given classification and scoring unit types.
     *
     * @param classificationUnit the classification unit type
     * @param scoringUnit        the scoring unit type
     * @return the classification module
     */
    public AFClassification getClassificationModule(SupportedClassificationUnit classificationUnit, SupportedScoringUnit scoringUnit) {
        AFClassification classification = new AFClassification();
        if (classificationUnit.equals(SupportedClassificationUnit.BASIC)) {
            classification.setClassificationModule(new BaseClassificationUnit());
            //add alternative classification units here into else if
        } else {
            System.err.println("Scoring unit not supported. Picking default BaseClassificationUnit.");
            classification.setClassificationModule(new BaseClassificationUnit());
        }

        if (scoringUnit.equals(SupportedScoringUnit.BASIC)) {
            classification.setScoringModule(new BaseScoringUnit());
            //add alternative scoring units here into else if
        } else {
            System.err.println("Scoring unit not supported. Picking default BaseScoringUnit.");
            classification.setScoringModule(new BaseScoringUnit());
        }
        return classification;
    }

}
