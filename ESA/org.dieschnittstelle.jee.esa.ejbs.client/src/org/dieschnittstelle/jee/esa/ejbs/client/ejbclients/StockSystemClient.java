package org.dieschnittstelle.jee.esa.ejbs.client.ejbclients;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemRemote;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;

import org.diesch

public class StockSystemClient implements StockSystemRemote {

	private StockSystemRemote proxy;
	
	public StockSystemClient() throws Exception {
		Context context = new InitialContext();
		
		this.proxy = (StockSystemRemote) context
				.lookup("ejb:global/org.dieschnittstelle.jee.esa.ejbs/org.dieschnittstelle.jee.esa.shared.ejbmodule.erp/StockSystemSingleton!org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemRemote");
		
	}
	
	
	@Override
	public void addToStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
		this.proxy.addToStock(product, pointOfSaleId, units);
	}

	@Override
	public void removeFromStock(IndividualisedProductItem product, long pointOfSaleId,
			int units) {
		this.proxy.removeFromStock(product, pointOfSaleId, units);
	}

	@Override
	public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
		return this.proxy.getProductsOnStock(pointOfSaleId);
	}

	@Override
	public List<IndividualisedProductItem> getAllProductsOnStock() {
		return this.proxy.getAllProductsOnStock();
	}

	@Override
	public int getUnitsOnStock(IndividualisedProductItem product, long pointOfSaleId) {
		return this.proxy.getUnitsOnStock(product, pointOfSaleId);
	}

	@Override
	public int getTotalUnitsOnStock(IndividualisedProductItem product) {
		return this.proxy.getTotalUnitsOnStock(product);
	}

	@Override
	public List<Integer> getPointsOfSale(IndividualisedProductItem product) {
		return this.proxy.getPointsOfSale(product);
	}


}
