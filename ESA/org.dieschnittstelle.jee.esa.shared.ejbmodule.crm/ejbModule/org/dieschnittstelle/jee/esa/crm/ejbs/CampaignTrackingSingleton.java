package org.dieschnittstelle.jee.esa.crm.ejbs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.CampaignExecution;
import org.jboss.logging.Logger;

/**
 * tracks the execution of a compaign
 */
@Singleton
/*
 * UE EJB1: kommentieren Sie die @Startup Annotation aus
 */
@Startup
@javax.ejb.ConcurrencyManagement(javax.ejb.ConcurrencyManagementType.CONTAINER)
public class CampaignTrackingSingleton implements CampaignTrackingRemote {

	protected static Logger logger = Logger.getLogger(CampaignTrackingSingleton.class);
	
	/**
	 * a map that associates touchpoint ids with campaign ids (we assume that
	 * for each touchpoint id there may only exist a single campaign execution
	 * for a given campaign id)
	 */
	private Map<Long, Map<Long, CampaignExecution>> campaignExecutionsAtTouchpoint = new HashMap<Long, Map<Long, CampaignExecution>>();

	public CampaignTrackingSingleton() {
		logger.info("<constructor>: " + this);
	}
	
	/**
	 * add a campaign execution
	 */
	@javax.ejb.Lock(javax.ejb.LockType.WRITE)
	public void addCampaignExecution(CampaignExecution campaign) {
		logger.info("addCampaignExecution(): " + campaign);
		
		if (!this.campaignExecutionsAtTouchpoint.containsKey(campaign
				.getTouchpoint().getId())) {
			this.campaignExecutionsAtTouchpoint.put(campaign.getTouchpoint()
					.getId(), new HashMap<Long, CampaignExecution>());
		}
		this.campaignExecutionsAtTouchpoint.get(
				campaign.getTouchpoint().getId()).put(
				campaign.getErpCampaignId(), campaign);
	}

	/**
	 * check whether for some product id there exists a campaign at some
	 * touchpoint and return its available units
	 */
	@javax.ejb.Lock(javax.ejb.LockType.READ)
	@javax.ejb.AccessTimeout(value=5,unit=java.util.concurrent.TimeUnit.SECONDS)
	public int existsValidCampaignExecutionAtTouchpoint(long erpProductId,
			AbstractTouchpoint tp) {
		logger.info("existsValidCampaignExecutionAtTouchpoint(): " + erpProductId + "@" + tp);

		Map<Long, CampaignExecution> campaignExecutions = this.campaignExecutionsAtTouchpoint
				.get(tp.getId());
		if (campaignExecutions == null) {
			logger.warn("no CampaignExecution found for touchpoint " + tp + " in " + this.campaignExecutionsAtTouchpoint);
			return 0;
		}
		CampaignExecution ce = campaignExecutions.get(erpProductId);
		if (ce == null) {
			logger.warn("no CampaignExecution found for product id " + erpProductId + " in " + this.campaignExecutionsAtTouchpoint);
			return 0;
		}
		if (!ce.isValid()) {
			logger.warn("CampaignExecution " + ce + " is not valid!");
			return 0;
		}

		return ce.getUnitsLeft();
	}

	/**
	 * purchase some units of some campaign at some touchpoint
	 */
	@javax.ejb.Lock(javax.ejb.LockType.WRITE)
	public void purchaseCampaignAtTouchpoint(long erpProductId,
			AbstractTouchpoint tp, int units) {
		logger.info("purchaseCampaignAtTouchpoint(): " + erpProductId + "@" + tp + ":" + units);
		
		this.campaignExecutionsAtTouchpoint.get(tp.getId()).get(erpProductId)
				.purchase(units);
	}
	
	public List<CampaignExecution> getAllCampaignExecutions() {
		List<CampaignExecution> campaigns = new ArrayList<CampaignExecution>();
		for (long tpid : campaignExecutionsAtTouchpoint.keySet()) {
			for (long cpid : campaignExecutionsAtTouchpoint.get(tpid).keySet()) {
				campaigns.add(campaignExecutionsAtTouchpoint.get(tpid).get(cpid));
			}
		}
		
		return campaigns;
	}
	
	
	@PostConstruct
	public void start() {
		logger.info("@PostConstruct");
	}

	@PreDestroy
	public void ende() {
		logger.info("@PreDestroy");
	}


}
