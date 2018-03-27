package service.afclassification.computational.scm;

import model.afclassification.Client;
import model.afclassification.Purpose;
import model.afclassification.Severity;

import java.util.List;


public interface Scoring {

    public Double scoreField(List<String> possibleValues, String actualValue, Purpose purpose, Severity severity,
                             Client client);

    public Double scoreField(Purpose purpose, Severity severity, Client client);

}
