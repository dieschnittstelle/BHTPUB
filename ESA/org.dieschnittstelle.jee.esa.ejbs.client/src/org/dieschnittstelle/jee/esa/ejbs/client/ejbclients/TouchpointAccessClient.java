package org.dieschnittstelle.jee.esa.ejbs.client.ejbclients;

import static org.dieschnittstelle.jee.esa.ejbs.client.Constants.TOUCHPOINT_1;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.dieschnittstelle.jee.esa.crm.ejbs.TouchpointAccessRemote;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.TouchpointCRUDRemote;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.ejbs.client.Constants;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDRemote;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;

public class TouchpointAccessClient implements TouchpointAccessRemote {
	
	private TouchpointAccessRemote touchpointAccessProxy;
	
	public TouchpointAccessClient() throws Exception {
		Context context = new InitialContext();
		this.touchpointAccessProxy = (TouchpointAccessRemote) context.lookup(Constants.TOUCHPOINT_ACCESS_BEAN);
	}
	
	
	public List<AbstractTouchpoint> readAllTouchpoints() {
		return touchpointAccessProxy.readAllTouchpoints();
	}

	@Override
	public AbstractTouchpoint createTouchpoint(AbstractTouchpoint touchpoint) {
		AbstractTouchpoint created = touchpointAccessProxy.createTouchpoint(touchpoint);
		touchpoint.setId(created.getId());
		touchpoint.setErpPointOfSaleId(created.getErpPointOfSaleId());
		
		return created;
	}
		
}
