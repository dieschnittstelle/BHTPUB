package org.dieschnittstelle.jee.esa.jaxrs;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.dieschnittstelle.jee.esa.entities.GenericCRUDExecutor;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;

/*
UE JRS2: implementieren Sie hier die im Interface deklarierten Methoden
 */

public class ProductCRUDWebServiceImpl implements IProductCRUDWebService {

	@Override
	public IndividualisedProductItem createProduct(
			IndividualisedProductItem prod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IndividualisedProductItem> readAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IndividualisedProductItem updateProduct(long id,
			IndividualisedProductItem update) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteProduct(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IndividualisedProductItem readProduct(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
