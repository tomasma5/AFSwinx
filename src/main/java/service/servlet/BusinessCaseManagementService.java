package service.servlet;

import model.Application;
import model.afclassification.BusinessCase;
import org.bson.types.ObjectId;

import java.util.List;

public interface BusinessCaseManagementService {

    public void createBusinessCase(BusinessCase bc);

    public void removeBusinessCase(ObjectId id);

    public void updateBusinessCase(BusinessCase bc);

    public BusinessCase findById(ObjectId id);

    public List<BusinessCase> getAllByApplication(ObjectId objectId);

    public BusinessCase findOrCreateBusinessCase(String businessCaseId);

    public List<BusinessCase> getAll();

}
