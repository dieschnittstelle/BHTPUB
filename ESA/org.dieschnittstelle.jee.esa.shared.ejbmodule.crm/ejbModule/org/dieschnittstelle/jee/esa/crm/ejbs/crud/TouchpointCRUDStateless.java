package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dieschnittstelle.jee.esa.crm.ejbs.ShoppingException;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.jboss.logging.Logger;

@Stateless
public class TouchpointCRUDStateless implements TouchpointCRUDRemote,
		TouchpointCRUDLocal {

	protected static Logger logger = Logger
			.getLogger(TouchpointCRUDStateless.class);

	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;

	/*
	 * UE ADD1: run CreateTouchoints in the non-ws client project with the @TransactionAttribute commented in - what happens?
	 */
	@Override
	//@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public AbstractTouchpoint createTouchpoint(AbstractTouchpoint touchpoint) {

		/*
		 * UE ADD1: swap true/false
		 */		
		if (/*true*/false) {
			throw new ShoppingException(
					ShoppingException.ShoppingSessionExceptionReason.UNKNOWN);
		} else {
			logger.info("createTouchpoint(): before persist(): " + touchpoint);
			em.persist(touchpoint);

			logger.info("createTouchpoint(): after persist(): " + touchpoint);

			return touchpoint;
		}

	}

	@Override
	public AbstractTouchpoint readTouchpoint(long id) {
		logger.info("readTouchpoint(): " + id);

		AbstractTouchpoint touchpoint = em.find(AbstractTouchpoint.class, id);

		logger.info("readTouchpoint(): " + touchpoint);

		return touchpoint;
	}

	@Override
	public AbstractTouchpoint updateTouchpoint(AbstractTouchpoint touchpoint) {
		logger.info("updateTouchpoint(): before merge(): " + touchpoint);
		touchpoint = em.merge(touchpoint);

		logger.info("updateTouchpoint(): after merge(): " + touchpoint);
		return touchpoint;
	}

	@Override
	public boolean deleteTouchpoint(int id) {
		logger.info("deleteTouchpoint(): " + id);

		em.remove(em.find(AbstractTouchpoint.class, id));

		logger.info("deleteTouchpoint(): done");

		return true;
	}

	@Override
	public List<AbstractTouchpoint> readAllTouchpoints() {
		logger.info("readAllTouchpoints()");

		Query query = em.createQuery("FROM AbstractTouchpoint");

		List<AbstractTouchpoint> tps = (List<AbstractTouchpoint>) query
				.getResultList();

		logger.info("readAllTouchpoints(): " + tps);

		return tps;
	}

}
