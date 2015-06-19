package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.jboss.logging.Logger;

/**
 * very rudimentary implementation without any logging... 
 */
@Stateless
public class PointOfSaleCRUDStateless implements PointOfSaleCRUDRemote, PointOfSaleCRUDLocal {

	protected static Logger logger = Logger.getLogger(PointOfSaleCRUDStateless.class);
	
	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;
	
	/*
	 * UE ADD1: comment out/in @TransactionAttribute
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public PointOfSale createPointOfSale(PointOfSale pos) {
		logger.info("createPointOfSale(): before persist: " + pos);
		
		em.persist(pos);		

		logger.info("createPointOfSale(): after persist: " + pos);

		return pos;
	}

	@Override
	public PointOfSale readPointOfSale(int posId) {
		return em.find(PointOfSale.class,posId);
	}

	@Override
	public boolean deletePointOfSale(int posId) {
		em.remove(em.find(PointOfSale.class,posId));
		return true;
	}

	@Override
	public List<PointOfSale> readAllPointsOfSale() {
		return em.createQuery("FROM PointOfSale").getResultList();
	}

}
