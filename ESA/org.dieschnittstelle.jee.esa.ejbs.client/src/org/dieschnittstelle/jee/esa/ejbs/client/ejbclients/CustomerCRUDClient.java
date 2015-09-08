package org.dieschnittstelle.jee.esa.ejbs.client.ejbclients;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.dieschnittstelle.jee.esa.crm.ejbs.CampaignTrackingRemote;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerCRUDRemote;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.ejbs.client.Constants;

public class CustomerCRUDClient implements CustomerCRUDRemote {

	private CustomerCRUDRemote proxy;

	public CustomerCRUDClient() throws Exception {
		Context context = new InitialContext();
		proxy = (CustomerCRUDRemote) context
				.lookup(Constants.CUSTOMER_CRUD_BEAN);
	}

	@Override
	public Customer readCustomerForEmail(String email) {
		return proxy.readCustomerForEmail(email);
	}

	@Override
	public Customer createCustomer(Customer customer) {
		Customer created = proxy.createCustomer(customer);
		
		// as a side-effect, we set the id on the customer object
		customer.setId(created.getId());
		
		return created;
	}

	@Override
	public Customer readCustomer(long id) {
		return proxy.readCustomer(id);
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		return proxy.updateCustomer(customer);
	}

	@Override
	public Customer updateCustomerWithSleep(Customer customer, long sleep) {
		return proxy.updateCustomerWithSleep(customer, sleep);
	}

	@Override
	public boolean deleteCustomer(int id) {
		return proxy.deleteCustomer(id);
	}

}
