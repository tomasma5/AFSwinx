package com.tomscz.afserver.manager;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.IdGenerator;
import com.tomscz.afserver.persistence.entity.*;
import com.tomscz.afserver.ws.security.AFSecurityContext;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

@Stateless(name = BusinessTripPartManagerImpl.name)
public class BusinessTripPartManagerImpl extends BaseManager<BusinessTripPart>
        implements BusinessTripPartManager<BusinessTripPart>, Serializable {

    @EJB
    private BusinessTripManager<BusinessTrip> businessTripManager;

    @EJB
    private AddressManager<Address> addressManager;

    @EJB
    private VehicleManager<Vehicle> vehicleManager;

    public static final String name = "BusinessTripPartManager";

    private static final long serialVersionUID = 1L;

    @Override
    public BusinessTripPart findById(int id) throws BusinessException {
        TypedQuery<BusinessTripPart> query = em.createQuery(
                "SELECT btp FROM BusinessTripPart btp WHERE btp.id = :id", BusinessTripPart.class);
        List<BusinessTripPart> businessTripParts = query.setParameter("id", id).getResultList();
        if (!businessTripParts.isEmpty()) {
            return businessTripParts.get(0);
        }
        return null;
    }

    @Override
    public List<BusinessTripPart> getAllBusinessTripParts(int businessTripId) throws BusinessException {
        return getBusinessTripParts(businessTripId);
    }

    @Override
    public List<BusinessTripPart> getBusinessTripsPartsForPerson(String username, int businessTripId, AFSecurityContext securityContext) throws BusinessException {
        if (!securityContext.isUserInRole(UserRoles.ADMIN) && !securityContext.getLoggedUserName().equals(username)) {
            throw new BusinessException(Response.Status.FORBIDDEN);
        }
        return getBusinessTripParts(businessTripId);
    }

    @Override
    public void createOrUpdate(BusinessTripPart businessTripPart, int businessTripId, String username, AFSecurityContext securityContext) throws BusinessException {
        if (!securityContext.isUserInRole(UserRoles.ADMIN)) {
            if (!username.equals(securityContext.getLoggedUserName())) {
                throw new BusinessException(Response.Status.FORBIDDEN);
            }
        }
        if (businessTripPart.getId() == 0) {
            businessTripPart = createNewBusinessTripPart(businessTripPart, businessTripId, securityContext);
        } else {
            businessTripPart = updateExistingBusinessTripPart(businessTripPart, securityContext);
        }
        super.createOrupdate(businessTripPart);
    }

    @Override
    public void remove(int businessTripPartId, String username, AFSecurityContext securityContext) throws BusinessException {
        if (!securityContext.isUserInRole(UserRoles.ADMIN)) {
            if (!username.equals(securityContext.getLoggedUserName())) {
                throw new BusinessException(Response.Status.FORBIDDEN);
            }
        }
        BusinessTripPart businessTripPart = findById(businessTripPartId);
        if(businessTripPart == null){
            throw new BusinessException(Response.Status.NOT_FOUND);
        }
        BusinessTrip businessTrip = businessTripManager.findById(businessTripPart.getBusinessTrip().getId());
        if (businessTrip == null || (!securityContext.isUserInRole(UserRoles.ADMIN) && !businessTrip.getStatus().equals(BusinessTripState.INPROGRESS))){
           throw new BusinessException(Response.Status.BAD_REQUEST);
        }

        delete(businessTripPart);
    }

    private List<BusinessTripPart> getBusinessTripParts(int businessTripId) throws BusinessException {
        BusinessTrip businessTrip = businessTripManager.findById(businessTripId);
        if (businessTrip != null) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<BusinessTripPart> btpQuery = cb.createQuery(BusinessTripPart.class);
            Root<BusinessTripPart> rootBtpQuery = btpQuery.from(BusinessTripPart.class);
            Predicate businessTripPredicate = cb.equal(rootBtpQuery.get("businessTrip"), businessTrip);
            btpQuery.where(businessTripPredicate);
            TypedQuery<BusinessTripPart> typedQuery = em.createQuery(btpQuery);
            return typedQuery.getResultList();
        }
        return null;
    }

    private BusinessTripPart createNewBusinessTripPart(BusinessTripPart businessTripPart, int businessTripId,
                                                   AFSecurityContext securityContext) throws BusinessException {
        BusinessTrip businessTrip = businessTripManager.findById(businessTripId);
        if(businessTrip == null || (!securityContext.isUserInRole(UserRoles.ADMIN) && !businessTrip.getStatus().equals(BusinessTripState.INPROGRESS))){
            throw new BusinessException(Response.Status.BAD_REQUEST);
        }
        if(businessTripPart.getStartDate().before(businessTripPart.getBusinessTrip().getStartDate()) ||
                businessTripPart.getEndDate().after(businessTripPart.getBusinessTrip().getEndDate())){
            throw new BusinessException(Response.Status.BAD_REQUEST);
        }
        businessTripPart.setBusinessTrip(businessTrip);

        businessTripPart.setId(IdGenerator.getNextBusinessTripPartId());

        Address startPlace = addressManager.findById(businessTripPart.getStartPlace().getId());
        if(startPlace == null){
            startPlace = businessTripPart.getStartPlace();
            startPlace.setId(IdGenerator.getNextAddressId());
            addressManager.createOrupdate(startPlace);
        }
        businessTripPart.setStartPlace(startPlace);
        Address endPlace = addressManager.findById(businessTripPart.getStartPlace().getId());
        if(endPlace == null){
            endPlace = businessTripPart.getEndPlace();
            endPlace.setId(IdGenerator.getNextAddressId());
            addressManager.createOrupdate(endPlace);
        }
        businessTripPart.setEndPlace(endPlace);

        //update total distance in business trip and vehicle
        businessTrip.setTotalDistance(businessTrip.getTotalDistance() + businessTripPart.getDistance());
        Vehicle vehicle = vehicleManager.findById(businessTrip.getVehicle().getId());
        if(vehicle != null){
            vehicle.setTachometerKilometers(vehicle.getTachometerKilometers() + businessTripPart.getDistance());
            vehicleManager.createOrupdate(vehicle);
        }
        businessTripManager.createOrUpdate(businessTrip, securityContext.getLoggedUserName(), securityContext);

        return businessTripPart;
    }

    private BusinessTripPart updateExistingBusinessTripPart(BusinessTripPart businessTripPart, AFSecurityContext securityContext) throws BusinessException {
        BusinessTripPart existingBusinessTripPart = findById(businessTripPart.getId());
        BusinessTrip businessTrip = existingBusinessTripPart.getBusinessTrip();
        if (businessTrip == null || (!securityContext.isUserInRole(UserRoles.ADMIN)
                && !existingBusinessTripPart.getBusinessTrip().getStatus().equals(BusinessTripState.INPROGRESS))) {
            throw new BusinessException(Response.Status.BAD_REQUEST);
        }
        if(businessTripPart.getDistance() <= 0 || (businessTripPart.getStartDate().before(businessTripPart.getBusinessTrip().getStartDate()) ||
                businessTripPart.getEndDate().after(businessTripPart.getBusinessTrip().getEndDate()))){
            throw new BusinessException(Response.Status.BAD_REQUEST);
        }
        existingBusinessTripPart.setStartDate(businessTripPart.getStartDate());
        existingBusinessTripPart.setEndDate(businessTripPart.getEndDate());

        //subtract existing distance value and add new value to bussiness trip and to vehicle
        businessTrip.setTotalDistance(businessTrip.getTotalDistance() - existingBusinessTripPart.getDistance() + businessTripPart.getDistance());
        Vehicle vehicle = vehicleManager.findById(businessTrip.getVehicle().getId());
        if(vehicle != null){
            vehicle.setTachometerKilometers(vehicle.getTachometerKilometers() - existingBusinessTripPart.getDistance() + businessTripPart.getDistance());
            vehicleManager.createOrupdate(vehicle);
        }
        businessTripManager.createOrUpdate(businessTrip, securityContext.getLoggedUserName(), securityContext);

        existingBusinessTripPart.setDistance(businessTripPart.getDistance());

        Address startPlace = addressManager.findById(businessTripPart.getStartPlace().getId());
        if(startPlace == null){
            startPlace = businessTripPart.getStartPlace();
            startPlace.setId(IdGenerator.getNextAddressId());
            addressManager.createOrupdate(startPlace);
        }
        existingBusinessTripPart.setStartPlace(startPlace);
        Address endPlace = addressManager.findById(businessTripPart.getStartPlace().getId());
        if(endPlace == null){
            endPlace = businessTripPart.getEndPlace();
            endPlace.setId(IdGenerator.getNextAddressId());
            addressManager.createOrupdate(endPlace);
        }
        existingBusinessTripPart.setEndPlace(endPlace);

        return existingBusinessTripPart;
    }
}
