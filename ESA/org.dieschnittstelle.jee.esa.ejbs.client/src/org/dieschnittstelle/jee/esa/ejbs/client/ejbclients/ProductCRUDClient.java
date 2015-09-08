package org.dieschnittstelle.jee.esa.ejbs.client.ejbclients;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.dieschnittstelle.jee.esa.ejbs.client.Constants;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDRemote;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;

public class ProductCRUDClient implements ProductCRUDRemote {

	private ProductCRUDRemote proxy;

	public ProductCRUDClient() throws Exception {
		// obtain the beans using a jndi context
//		Context context = new InitialContext();
//		proxy = (ProductCRUDRemote) context
//				.lookup("");
	}

	public AbstractProduct createProduct(AbstractProduct prod) {

		// JPA3: KOMMENTIEREN SIE DIE FOLGENDE ZUWEISUNG VON IDs UND DIE RETURN-ANWEISUNG AUS
		prod.setId(Constants.nextId());
		return prod;

		// JPA3: KOMMENTIEREN SIE DEN FOLGENDEN CODE EIN		
//		AbstractProduct created = proxy.createProduct(prod);
//		// as a side-effect we set the id of the created product on the argument before returning
//		prod.setId(created.getId());
//		return created;
	}

	public List<AbstractProduct> readAllProducts() {
//		return proxy.readAllProducts();
		return null;
	}

	public AbstractProduct updateProduct(AbstractProduct update) {
//		return proxy.updateProduct(update);
		return null;
	}

	public AbstractProduct readProduct(long productID) {
//		return proxy.readProduct(productID);
		return null;
	}

	public boolean deleteProduct(long productID) {
//		return proxy.deleteProduct(productID);
		return false;
	}

}
