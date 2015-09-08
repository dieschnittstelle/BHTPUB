package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.Local;

import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

@Local
public interface StockItemCRUDLocal {
	
	public StockItem createStockItem(StockItem item);
	
	public StockItem readStockItem(IndividualisedProductItem prod, PointOfSale pos);

	public StockItem updateStockItem(StockItem item);
	
	public List<StockItem> readAllStockItems();
	
	public List<StockItem> readStockItemsForProduct(IndividualisedProductItem prod);
	
	public List<StockItem> readStockItemForPointOfSale(PointOfSale pos);
	
}