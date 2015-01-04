package com.tomscz.afserver.manager;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.Address;
import com.tomscz.afserver.persistence.entity.Country;

@Stateless(name=AddressManagerImpl.name)
public class AddressManagerImpl extends BaseManager<Address> implements AddressManager<Address>, Serializable{
    
    private static final long serialVersionUID = 1L;
    
    public static final String name="AdressManager";

    @Override
    public Address findById(int id) throws BusinessException {
        TypedQuery<Address> query =
                em.createQuery("SELECT c FROM Address c WHERE c.id = :id",
                    Address.class);
        List<Address> address = query.setParameter("id", id).getResultList();
        if(!address.isEmpty()){
            return address.get(0);
        }
        return null;
    }

    @Override
    public List<Address> findAddressInCountry(Country c) throws BusinessException{
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Address> countryQuery = cb.createQuery(Address.class);
        Root<Address> rootCountryQuery = countryQuery.from(Address.class);
        Predicate countryPredicate = cb.equal(rootCountryQuery.get("country"), c);
        countryQuery.where(countryPredicate);
        TypedQuery<Address> typedQuery = em.createQuery(countryQuery);
        List<Address> resultList = typedQuery.getResultList();
        return resultList;
    }
    
}
