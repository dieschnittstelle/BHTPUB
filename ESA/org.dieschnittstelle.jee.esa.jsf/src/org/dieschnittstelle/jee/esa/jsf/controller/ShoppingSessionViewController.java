package org.dieschnittstelle.jee.esa.jsf.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectBoolean;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import org.dieschnittstelle.jee.esa.crm.ejbs.TouchpointAccessLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.jsf.model.ShoppingCartModel;
import org.jboss.logging.Logger;

// see the faces-config.xml where the following properties are declared as well as the managed property for the shoppingCartModel
//@ManagedBean(name = "shoppingSessionVC")
//@SessionScoped
public class ShoppingSessionViewController {

	private static Logger logger = Logger
			.getLogger(ShoppingSessionViewController.class);

	/*
	 * model elements
	 */

	/**
	 * for the shopping Cart we use a managed bean as model because this
	 * simplifies sharing data between ourselves and the ProductsViewController
	 */
	//@ManagedProperty(value = "#{shoppingCartModel}")
	private ShoppingCartModel shoppingCartModel;

	/**
	 * for accessing touchpoints we use a EJB client
	 */
	@EJB(mappedName="java:global/org.dieschnittstelle.jee.esa.ejbs/org.dieschnittstelle.jee.esa.shared.ejbmodule.crm/TouchpointAccessStateless!org.dieschnittstelle.jee.esa.crm.ejbs.TouchpointAccessLocal")
	private TouchpointAccessLocal touchpointAccess;
	
	/**
	 * this is the touchpoint selected by the user
	 */
	private AbstractTouchpoint touchpoint;

	/**
	 * for accessing customers we use the customer CRUD
	 */
	@EJB(mappedName="java:global/org.dieschnittstelle.jee.esa.ejbs/org.dieschnittstelle.jee.esa.shared.ejbmodule.crm/CustomerCRUDStateless!org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerCRUDLocal")
	private CustomerCRUDLocal customerAccess;
	
	/**
	 * this attribute holds the customer data
	 */
	private Customer customer = new Customer();

	/**
	 * track whether the customer shall be created or whether we have an
	 * existing customer
	 */
	private boolean newCustomer;

	/**
	 * a map of touchpoints that is used for handling the conversion of
	 * touchpoint objects to string values that is required for offering
	 * touchpoints via a browser-side selection element
	 */
	private Map<String, AbstractTouchpoint> touchpointsMap = new HashMap<String, AbstractTouchpoint>();

	/*
	 * view elements
	 */
	/**
	 * this exemplifies the usage of a view element representation inside of the
	 * controller
	 */
	private UICommand doPurchaseCommand;

	/*
	 * lifecycle
	 */

	public ShoppingSessionViewController() {
		logger.info("<constructor>");
	}

	@PostConstruct
	public void initialise() {
		logger.info("@PostConstruct: shoppingCartModel is: "
				+ shoppingCartModel);
		logger.info("@PostConstruct: doPurchaseCommand is: "
				+ doPurchaseCommand);
		logger.info("@PostConstruct: touchpointAccess is: " + touchpointAccess);
	}

	/*
	 * model accessors
	 */
	public void setShoppingCartModel(ShoppingCartModel shoppingCartModel) {
		logger.info("setShoppingCartModel(): " + shoppingCartModel);
		this.shoppingCartModel = shoppingCartModel;
	}
	
	public void setCustomer(Customer customer) {
		logger.info("setCustomer(): " + customer);
		this.customer = customer;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public AbstractTouchpoint getTouchpoint() {
		return touchpoint;
	}

	public void setTouchpoint(AbstractTouchpoint touchpoint) {
		logger.info("setTouchpoint(): " + touchpoint);
		this.touchpoint = touchpoint;
	}

	public boolean isNewCustomer() {
		return newCustomer;
	}

	public void setNewCustomer(boolean newCustomer) {
		this.newCustomer = newCustomer;
	}

	/*
	 * view accessors
	 */

	public UICommand getDoPurchaseCommand() {
		logger.info("getDoPurchaseCommand(): " + this.doPurchaseCommand);
		return doPurchaseCommand;
	}

	public void setDoPurchaseCommand(UICommand doPurchaseCommand) {
		logger.info("setDoPurchaseCommand(): " + doPurchaseCommand);
	}

	/*
	 * event handling
	 */

	/**
	 * this is called by an ajax request
	 * 
	 * @param event
	 */
	public void onNewCustomerCheckChanged(AjaxBehaviorEvent event) {
		// via event.getSource() the source ui element for which the request was
		// created can be obtained, and values can be read out from this element
		logger.info("onNewCustomerCheckChanged(): " + event.getSource());
		boolean selected = ((UISelectBoolean) event.getSource()).isSelected();
		logger.info("onNewCustomerCheckChanged(): selected: " + selected);

		this.newCustomer = selected;
	}

	public void onTouchpointSelectionChanged(ValueChangeEvent event) {
		logger.info("onTouchpointSelectionChanged: " + event);
		logger.info("onTouchpointSelectionChanged: component is: "
				+ event.getComponent());

		UISelectOne comp = (UISelectOne) event.getComponent();
		logger.info("onTouchpointSelected(): value is: " + comp.getValue());

		this.touchpoint = (AbstractTouchpoint) comp.getValue();

		// we need to update the price calculation on the products based on the
		// price a product is assigned for the given touchpoint

		/*
		 * U2: implement this using the StockSystem EJB
		 */

	}

	/*
	 * validation
	 */
	public void validateUnitsUpdate(FacesContext context, UIComponent comp,
			Object value) {

		logger.info("validateUnitsUpdate(): value is: " + value);
		logger.info("validateUnitsUpdate(): component is: " + comp);

		Map<String, Object> attributes = comp.getAttributes();
		logger.info("validateUnitsUpdate(): attributes are: " + attributes);
		// the id of the product to be updated can be obtained from the
		// attributes on the UIComponent that has been declared in the facelet
		long id = (Long) attributes.get("erpProductId");
		logger.info("validateUnitsUpdate(): id of product is: " + id);

		/*
		 * here validate availability of the selected product in the selected
		 * shop - this is currently done as a dummy
		 */
		/*
		 * U2 use StockSystem EJB
		 */
		if (this.touchpoint != null && ((Integer) value) == 10) {
			context.addMessage("cartTable", new FacesMessage(
					javax.faces.application.FacesMessage.SEVERITY_ERROR,
					"Uneinloesbarer Warenkorb",
					"Keine Filiale hat die Produkte ausreichend verfuegbar!"));
			context.validationFailed();
			context.renderResponse();
		}
	}

	/**
	 * obtain the touchpoints at which the selected products are available in
	 * the given quantity
	 */
	public List<String> getAvailableTouchpoints() {
		logger.info("getAvailableTouchpoints()");

		this.touchpointsMap.clear();
		List<String> tpnames = new ArrayList<String>();

		/*
		 * UE JSF2: here we need to check whether the products are available at the
		 * given touchpoints using StockSystem
		 */
		for (AbstractTouchpoint tp : touchpointAccess.readAllTouchpoints()) {
			// we use a local map for being able to convert between touchpoint
			// strings and touchpoint objects
			this.touchpointsMap.put(tp.getName(), tp);
			tpnames.add(tp.getName());
		}

		logger.info("getAvailableTouchpoints(): " + tpnames);

		return tpnames;
	}

	/*
	 * actions
	 */

	/**
	 * this method does not do that much because the actual udpdate is done via
	 * data binding from view to model
	 * 
	 * @return
	 */
	public String updateProductBundle() {
		logger.info("updateProductBundle()");
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		final String id = params.get("id");
		final String units = params.get("units");

		logger.info("updateProductBundle: (" + id + ":" + units + ")");

		return "";
	}

	/**
	 * 
	 * @return
	 */
	public String registerCustomer() {
		logger.info("registerCustomer(): " + this.customer);

		// access the faces contex
		FacesContext context = FacesContext.getCurrentInstance();
		
		// Here, we check whether an existing customer wants to login or whether a new customer wants to register
		if ("annam@example.com".equals(this.customer.getEmail())) {
			context.addMessage("customerForm", new FacesMessage(
					javax.faces.application.FacesMessage.SEVERITY_ERROR,
					"Unbekannte Mailadresse",
					"Die eingegebene Mailadresse Adresse ist uns nicht bekannt."));
			context.validationFailed();
			context.renderResponse();
		}
		
		return "";
	}
	
	/**
	 * 
	 * @return
	 */
	public String purchaseProducts() {
		logger.info("purchaseProducts(): "
				+ shoppingCartModel.getProductBundles());
		
		// we clear the shopping cart through invalidating the session, i.e. on the next request a new session should be created
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		// we add a meesage to be displayed on the products page
		// passing messages to follow-up pages is not trivial
//		FacesContext.getCurrentInstance().addMessage("messagePlaceholder", new FacesMessage(
//				javax.faces.application.FacesMessage.SEVERITY_INFO,
//				"Kauf erfolgreich",
//				"Vielen Dank fuer Ihren Einkauf!"));
		
		return "products";
	}

	/*
	 * converters
	 */

	/**
	 * this converter maps touchpoint names that are sent by the browser when
	 * changing the selection onto touchpoint objects and vice versa
	 * 
	 * @return
	 */
	public Converter getTouchpointConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext arg0, UIComponent arg1,
					String arg2) {
				logger.info("getAsObject(): " + arg2);
				return touchpointsMap.get(arg2);
			}

			@Override
			public String getAsString(FacesContext arg0, UIComponent arg1,
					Object arg2) {
				logger.info("getAsString(): " + arg2);
				return String.valueOf(arg2);
			}

		};
	}

}
