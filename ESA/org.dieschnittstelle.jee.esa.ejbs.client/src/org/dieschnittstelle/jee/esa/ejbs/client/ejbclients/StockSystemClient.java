package org.dieschnittstelle.jee.esa.ejbs.client.ejbclients;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemRemote;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;

public class StockSystemClient implements StockSystemRemote {

	private StockSystemRemote proxy;
	
	public StockSystemClient() throws Exception {
//		Context context = new InitialContext();
//		
//		this.proxy = (StockSystemRemote) context
//				.lookup("");
	}
	
	
	@Override
	public void addToStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
		//this.proxy.addToStock(product, pointOfSaleId, units);
	}

	@Override
	public void removeFromStock(IndividualisedProductItem product, long pointOfSaleId,
			int units) {
		//this.proxy.removeFromStock(product, pointOfSaleId, units);
	}

	@Override
	public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
		//return this.proxy.getProductsOnStock(pointOfSaleId);
		return null;
	}

	@Override
	public List<IndividualisedProductItem> getAllProductsOnStock() {
		//return this.proxy.getAllProductsOnStock();
		return null;
	}

	@Override
	public int getUnitsOnStock(IndividualisedProductItem product, long pointOfSaleId) {
		//return this.proxy.getUnitsOnStock(product, pointOfSaleId);
		return 0;
	}

	@Override
	public int getTotalUnitsOnStock(IndividualisedProductItem product) {
		//return this.proxy.getTotalUnitsOnStock(product);
		return 0;
	}

	@Override
	public List<Integer> getPointsOfSale(IndividualisedProductItem product) {
		//return this.proxy.getPointsOfSale(product);
		return null;
	}


}
