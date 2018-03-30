package service.afclassification.computational.scm;

import model.afclassification.Client;
import model.afclassification.Purpose;
import model.afclassification.Severity;

import java.util.List;

/**
 * Scoring should be used for givin field a score based on its purpose, severity and context information.
 */
public interface Scoring {

    /**
     * Returns score for field and given values, which helps to decide.
     * @param possibleValues possible values which can be inserted into the field
     * @param actualValue actual value from field
     * @param purpose purpose of the field
     * @param severity severity of the field
     * @param client context information
     * @return calculated score
     */
    public Double scoreField(List<String> possibleValues, String actualValue, Purpose purpose, Severity severity,
                             Client client);

    /**
     * Returns score for field and given values, which helps to decide.
     * @param purpose purpose of the field
     * @param severity severity of the field
     * @param client context information
     * @return calculated score
     */
    public Double scoreField(Purpose purpose, Severity severity, Client client);

}
