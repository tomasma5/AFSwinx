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

@Stateless(name = BusinessTripManagerImpl.name)
public class BusinessTripManagerImpl extends BaseManager<BusinessTrip> implements BusinessTripManager<BusinessTrip>, Serializable {

    @EJB
    private PersonManager<Person> personManager;

    @EJB
    private VehicleManager<Vehicle> vehicleManager;

    @EJB
    private AddressManager<Address> addressManager;

    public static final String name = "BusinessTripManager";

    private static final long serialVersionUID = 1L;

    @Override
    public List<BusinessTrip> getAllBusinessTrips() {
        return em.createQuery("select bt from BusinessTrip bt", BusinessTrip.class).getResultList();
    }

    @Override
    public List<BusinessTrip> getBusinessTripsForPerson(String username, AFSecurityContext securityContext) throws BusinessException {
        if (!securityContext.isUserInRole(UserRoles.ADMIN) && !securityContext.getLoggedUserName().equals(username)) {
            throw new BusinessException(Response.Status.FORBIDDEN);
        }
        Person person = personManager.findUser(username);
        if (person != null) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<BusinessTrip> btQuery = cb.createQuery(BusinessTrip.class);
            Root<BusinessTrip> rootBtQuery = btQuery.from(BusinessTrip.class);
            Predicate personPredicate = cb.equal(rootBtQuery.get("person"), person);
            btQuery.where(personPredicate);
            TypedQuery<BusinessTrip> typedQuery = em.createQuery(btQuery);
            return typedQuery.getResultList();
        }
        return null;
    }

    @Override
    public BusinessTrip findById(int id) throws BusinessException {
        TypedQuery<BusinessTrip> query = em.createQuery(
                "SELECT bt FROM BusinessTrip bt WHERE bt.id = :id", BusinessTrip.class);
        List<BusinessTrip> businessTrips = query.setParameter("id", id).getResultList();
        if (!businessTrips.isEmpty()) {
            return businessTrips.get(0);
        }
        return null;
    }

    @Override
    public void createOrUpdate(BusinessTrip businessTrip, String username,
                               AFSecurityContext securityContext) throws BusinessException {
        if (!securityContext.isUserInRole(UserRoles.ADMIN)) {
            if (!username.equals(securityContext.getLoggedUserName())) {
                throw new BusinessException(Response.Status.FORBIDDEN);
            }
        }
        if (businessTrip.getId() == 0) {
            businessTrip = createNewBusinessTrip(businessTrip, username);
        } else {
            businessTrip = updateExistingBusinessTrip(businessTrip, securityContext);
        }
        super.createOrupdate(businessTrip);
    }

    private BusinessTrip createNewBusinessTrip(BusinessTrip businessTrip, String username) throws BusinessException {
        businessTrip.setId(IdGenerator.getNextBusinessTripId());
        Vehicle vehicle = vehicleManager.findById(businessTrip.getVehicle().getId());
        Person person = personManager.findUser(username);
        if (person == null || vehicle == null) {
            throw new BusinessException(Response.Status.BAD_REQUEST);
        }
        businessTrip.setVehicle(vehicle);
        businessTrip.setPerson(person);

        Address startPlace = addressManager.findById(businessTrip.getStartPlace().getId());
        if (startPlace == null) {
            startPlace = businessTrip.getStartPlace();
            startPlace.setId(IdGenerator.getNextAddressId());
            addressManager.createOrupdate(startPlace);
        }
        businessTrip.setStartPlace(startPlace);
        Address endPlace = addressManager.findById(businessTrip.getStartPlace().getId());
        if (endPlace == null) {
            endPlace = businessTrip.getEndPlace();
            endPlace.setId(IdGenerator.getNextAddressId());
            addressManager.createOrupdate(endPlace);
        }
        businessTrip.setEndPlace(endPlace);
        businessTrip.setStatus(BusinessTripState.REQUESTED);

        return businessTrip;
    }

    private BusinessTrip updateExistingBusinessTrip(BusinessTrip businessTrip, AFSecurityContext securityContext) throws BusinessException {
        BusinessTrip existingBusinessTrip = findById(businessTrip.getId());
        if (!securityContext.isUserInRole(UserRoles.ADMIN) &&
                !(businessTrip.getStatus().name().equals(BusinessTripState.REQUESTED.name()) ||
                        businessTrip.getStatus().name().equals(BusinessTripState.INPROGRESS.name()) ||
                        businessTrip.getStatus().name().equals(BusinessTripState.CANCELLED.name()) ||
                        businessTrip.getStatus().name().equals(BusinessTripState.FINISHED.name()))) {
            throw new BusinessException(Response.Status.BAD_REQUEST);
        }
        //changing status
        if (!existingBusinessTrip.getStatus().name().equals(businessTrip.getStatus().name())) {
            if (businessTrip.getStatus().name().equals(BusinessTripState.FINISHED.name())) {
                if (!businessTrip.getStatus().name().equals(BusinessTripState.INPROGRESS.name())) {
                    //cannot finish trip that is not in progress
                    throw new BusinessException(Response.Status.BAD_REQUEST);
                }
            } else if (businessTrip.getStatus().name().equals(BusinessTripState.INPROGRESS.name())) {
                if (!businessTrip.getStatus().name().equals(BusinessTripState.ACCEPTED.name())) {
                    //cannot start trip that was not accepted
                    throw new BusinessException(Response.Status.BAD_REQUEST);
                }
            } else if (businessTrip.getStatus().name().equals(BusinessTripState.REQUESTED.name())){
                if (!existingBusinessTrip.getStatus().name().equals(BusinessTripState.CANCELLED.name())) {
                    //cannot set trip which is in progress or finished to requested
                    throw new BusinessException(Response.Status.BAD_REQUEST);
                }
            }
            existingBusinessTrip.setStatus(businessTrip.getStatus());
        }

        if (businessTrip.getStatus().name().equals(BusinessTripState.REQUESTED.name()) ||
                businessTrip.getStatus().name().equals(BusinessTripState.CANCELLED.name())) {
            //if it is only requested or cancelled information can be edited - except total distance = read only field
            existingBusinessTrip.setStartDate(businessTrip.getStartDate());
            existingBusinessTrip.setEndDate(businessTrip.getEndDate());
            existingBusinessTrip.setDescription(businessTrip.getDescription());
            Vehicle vehicle = vehicleManager.findById(businessTrip.getVehicle().getId());
            existingBusinessTrip.setVehicle(vehicle);
            Address startPlace = addressManager.findById(businessTrip.getStartPlace().getId());
            if (startPlace == null) {
                startPlace = businessTrip.getStartPlace();
                startPlace.setId(IdGenerator.getNextAddressId());
                addressManager.createOrupdate(startPlace);
            }
            existingBusinessTrip.setStartPlace(startPlace);
            Address endPlace = addressManager.findById(businessTrip.getEndPlace().getId());
            if (endPlace == null) {
                endPlace = businessTrip.getEndPlace();
                endPlace.setId(IdGenerator.getNextAddressId());
                addressManager.createOrupdate(endPlace);
            }
            existingBusinessTrip.setEndPlace(endPlace);
        }

        return existingBusinessTrip;
    }

}
