package org.dieschnittstelle.jee.esa.ejbs.client.shopping;

import java.util.List;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.crm.ejbs.CampaignTrackingRemote;
import org.dieschnittstelle.jee.esa.crm.ejbs.CustomerTrackingRemote;
import org.dieschnittstelle.jee.esa.crm.ejbs.ShoppingCartRemote;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.CrmProductBundle;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;
import org.dieschnittstelle.jee.esa.ejbs.client.ejbclients.CampaignTrackingClient;
import org.dieschnittstelle.jee.esa.ejbs.client.ejbclients.CustomerTrackingClient;
import org.dieschnittstelle.jee.esa.ejbs.client.ejbclients.ShoppingCartClient;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.Campaign;

public class ShoppingSession implements ShoppingBusinessDelegate {

	protected static Logger logger = Logger.getLogger(ShoppingSession.class);

	/*
	 * the three beans that are used
	 */
	private ShoppingCartRemote shoppingCart;

	private CustomerTrackingRemote customerTracking;

	private CampaignTrackingRemote campaignTracking;

	/**
	 * the customer
	 */
	private Customer customer;

	/**
	 * the touchpoint
	 */
	private AbstractTouchpoint touchpoint;

	public ShoppingSession() {
		logger.info("<constructor>");
	}

	/**
	 * access the beans that we need
	 */
	public void initialise() {
		try {
			this.campaignTracking = new CampaignTrackingClient();
			this.customerTracking = new CustomerTrackingClient();
			this.shoppingCart = new ShoppingCartClient();
		} catch (Exception e) {
			throw new RuntimeException("initialise() failed: " + e, e);
		}
	}

	public void setTouchpoint(AbstractTouchpoint touchpoint) {
		this.touchpoint = touchpoint;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void addProduct(AbstractProduct product, int units) {
		this.shoppingCart.addProductBundle(new CrmProductBundle(product.getId(), units, product instanceof Campaign));
	}

	/*
	 * verify whether campaigns are still valid
	 */
	public void verifyCampaigns() {
		if (this.customer == null || this.touchpoint == null) {
			throw new RuntimeException("cannot verify campaigns! No touchpoint has been set!");
		}

		for (CrmProductBundle productBundle : this.shoppingCart.getProductBundles()) {
			if (productBundle.isCampaign()) {
				int availableCampaigns = this.campaignTracking.existsValidCampaignExecutionAtTouchpoint(
						productBundle.getErpProductId(), this.touchpoint);
				logger.info("got available campaigns for product " + productBundle.getErpProductId() + ": "
						+ availableCampaigns);
				// we check whether we have sufficient campaign items available
				if (availableCampaigns < productBundle.getUnits()) {
					throw new RuntimeException("verifyCampaigns() failed for productBundle " + productBundle
							+ " at touchpoint " + this.touchpoint + "! Need " + productBundle.getUnits()
							+ " instances of campaign, but only got: " + availableCampaigns);
				}
			}
		}
	}

	public void purchase() {
		logger.info("purchase()");

		if (this.customer == null || this.touchpoint == null) {
			throw new RuntimeException(
					"cannot commit shopping session! Either customer or touchpoint has not been set: " + this.customer
							+ "/" + this.touchpoint);
		}

		// verify the campaigns
		verifyCampaigns();

		// remove the products from stock and campaign tracking
		checkAndRemoveProductsFromStock();
		
		// add a new customer transaction for the current purchase
		CustomerTransaction transaction = new CustomerTransaction(this.customer, this.touchpoint, this.shoppingCart.getProductBundles());
		transaction.setCompleted(true);
		customerTracking.createTransaction(transaction);

		logger.info("purchase(): done.\n");
	}

	/*
	 * to be implemented as server-side method for PAT2
	 */
	private void checkAndRemoveProductsFromStock() {
		logger.info("checkAndRemoveProductsFromStock");

		for (CrmProductBundle productBundle : this.shoppingCart.getProductBundles()) {
			if (productBundle.isCampaign()) {
				this.campaignTracking.purchaseCampaignAtTouchpoint(productBundle.getErpProductId(), this.touchpoint,
						productBundle.getUnits());
				// wenn Sie eine Kampagne haben, muessen Sie hier
				// 1) zunaechst das Campaign-Objekt anhand der erpProductId von productBundle auslesen
				// 2) dann ueber die ProductBundle Objekte auf dem Campaign Objekt iterieren und
				// 3) fuer jedes ProductBundle das betreffende Produkt in der auf dem Bundle angegebenen Anzahl, multipliziert mit dem Wert von 
				// productBundle.getUnits() aus dem Warenkorb, 
				// - hinsichtlich Verfuegbarkeit ueberpruefen, und
				// - falls verfuegbar aus dem Warenlager entfernen 
				// (Anm.: productBundle.getUnits() sagt Ihnen, wie oft ein Produkt, im vorliegenden Fall eine Kampagne, im
				// Warenkorb liegt)
			} else {
				// andernfalls (wenn keine Kampagne vorliegt) muessen Sie
				// 1) das Produkt (dann IndividualisedProductItem) anhand der erpProductId von productBundle auslesen, und
				// 2) das Produkt in der in productBundle.getUnits() angegebenen Anzahl hinsichtlich Verfuegbarkeit ueberpruefen und 
				// 3) das Produkt, falls verfuegbar, aus dem Warenlager entfernen

				// Schritt 1) koennen Sie ggf. auch mit Typ AbstractProduct vor
				// die if/else Verzweigung bezueglich isCampaign() platzieren -
				// in jedem Fall benoetigen Sie hierfuer Zugriff auf Ihre
				// ProductCRUD EJB
			}

		}
	}

}
