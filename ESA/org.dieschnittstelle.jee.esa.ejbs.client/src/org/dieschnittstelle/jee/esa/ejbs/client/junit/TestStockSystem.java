package org.dieschnittstelle.jee.esa.ejbs.client.junit;

import static org.dieschnittstelle.jee.esa.ejbs.client.Constants.*;

import java.util.List;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Address;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.dieschnittstelle.jee.esa.ejbs.client.Constants;
import org.dieschnittstelle.jee.esa.ejbs.client.ejbclients.ProductCRUDClient;
import org.dieschnittstelle.jee.esa.ejbs.client.ejbclients.StockSystemClient;
import org.dieschnittstelle.jee.esa.ejbs.client.ejbclients.TouchpointAccessClient;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;


public class TestStockSystem {
	
	private ProductCRUDClient productCRUDClient;
	private StockSystemClient stockSystemClient;
	private TouchpointAccessClient touchpointCRUDClient;
	
	@Before
	public void prepareContext() throws Exception {
		// we reset the ids on the local entities
		Constants.resetEntities();	
		// initialise the clients
		productCRUDClient = new ProductCRUDClient();		
		stockSystemClient = new StockSystemClient();		
		touchpointCRUDClient = new TouchpointAccessClient();
		// we create the touchpoints and products before creating the stock items as the actual subject of testing
		productCRUDClient.createProduct(PRODUCT_1);
		productCRUDClient.createProduct(PRODUCT_2);
		
		touchpointCRUDClient.createTouchpoint(TOUCHPOINT_1);
		touchpointCRUDClient.createTouchpoint(TOUCHPOINT_2);
	}
	
	@Test 
	public void stockSystemWorks() {
		// add to stock
		stockSystemClient.addToStock(PRODUCT_1, TOUCHPOINT_1.getErpPointOfSaleId(), 100);
		stockSystemClient.addToStock(PRODUCT_2, TOUCHPOINT_1.getErpPointOfSaleId(), 50);
		stockSystemClient.addToStock(PRODUCT_1, TOUCHPOINT_2.getErpPointOfSaleId(), 75);
		
		assertEquals("create/read correct for p1/tp1", 100, stockSystemClient.getUnitsOnStock(PRODUCT_1, TOUCHPOINT_1.getErpPointOfSaleId()));
		assertEquals("create/read correct for p2/tp1", 50, stockSystemClient.getUnitsOnStock(PRODUCT_2, TOUCHPOINT_1.getErpPointOfSaleId()));
		assertEquals("create/read correct for p1/tp2", 75, stockSystemClient.getUnitsOnStock(PRODUCT_1, TOUCHPOINT_2.getErpPointOfSaleId()));

		stockSystemClient.addToStock(PRODUCT_1, TOUCHPOINT_1.getErpPointOfSaleId(), 5);
		assertEquals("update/read correct for p1/tp1", 105, stockSystemClient.getUnitsOnStock(PRODUCT_1, TOUCHPOINT_1.getErpPointOfSaleId()));
		
		stockSystemClient.removeFromStock(PRODUCT_1, TOUCHPOINT_1.getErpPointOfSaleId(), 10);
		assertEquals("remove correct for p1/tp1", 95, stockSystemClient.getUnitsOnStock(PRODUCT_1, TOUCHPOINT_1.getErpPointOfSaleId()));

		// read out all products on stock for tp1 and tp2 and check whether size and content is correct
		List products_tp1 = stockSystemClient.getProductsOnStock(TOUCHPOINT_1.getErpPointOfSaleId());
		assertEquals("size of products at touchpoint correct for tp1", 2, products_tp1.size());

		List products_tp2 = stockSystemClient.getProductsOnStock(TOUCHPOINT_2.getErpPointOfSaleId());
		assertEquals("size of products at touchpoint correct for tp2", 1, products_tp2.size());		
		assertEquals("returned product for touchpoint correct for tp2", PRODUCT_1.getName(), ((IndividualisedProductItem)products_tp2.get(0)).getName());
		
		// check that total number of units is ok
		assertEquals("total units on stock correct for p1", 170, stockSystemClient.getTotalUnitsOnStock(PRODUCT_1));
		
		// check that we get the correct touchpoints for the products
		List<Integer> touchpoints_p1 = stockSystemClient.getPointsOfSale(PRODUCT_1);
		List<Integer> touchpoints_p2 = stockSystemClient.getPointsOfSale(PRODUCT_2);
			
		assertEquals("number of touchpoints correct for p1",2,touchpoints_p1.size());
		assertEquals("number of touchpoints correct for p2",1,touchpoints_p2.size());
		assertTrue("touchpoint correct for p2", touchpoints_p2.contains(new Long(TOUCHPOINT_1.getErpPointOfSaleId())));
		
}
	

}
