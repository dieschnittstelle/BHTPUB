package org.dieschnittstelle.jee.esa.erp.ejbs;

import java.util.List;

import javax.ejb.Local;

import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

@Local
public interface StockSystemLocal {

	/**
	 * adds some units of a product to the stock of a point of sale
	 *
	 * @param product
	 * @param pointOfSaleId
	 * @param units
	 */
	public void addToStock(IndividualisedProductItem product,long pointOfSaleId,int units);

	/**
	 * removes some units of a product from the stock of a point of sale
	 *	
	 * @param product
	 * @param pointOfSaleId
	 * @param units
	 */
	public void removeFromStock(IndividualisedProductItem product,long pointOfSaleId,int units);
	
	/**
	 * returns all products on stock of some pointOfSale
	 * 
	 * @param pointOfSaleId
	 * @return
	 */
	public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId);

	/**
	 * returns all products on stock
	 * 
	 * @return
	 */
	public List<IndividualisedProductItem> getAllProductsOnStock();

	/**
	 * returns the units on stock for a product at some point of sale
	 * 
	 * @param product
	 * @param pointOfSaleId
	 * @return
	 */
	public int getUnitsOnStock(IndividualisedProductItem product, long pointOfSaleId);

	/**
	 * returns the total number of units on stock for some product
	 * 
	 * @param product
	 * @return
	 */
	public int getTotalUnitsOnStock(IndividualisedProductItem product);
	
	/**
	 * returns the points of sale where some product is available
	 * 
	 * @param product
	 * @return
	 */
	public List<Integer> getPointsOfSale(IndividualisedProductItem product);


	/**
	 * returns the complete available stock
	 * 
	 * @return
	 */
	public List<StockItem> getCompleteStock();
	
}
