package org.dieschnittstelle.jee.esa.basics.reflection.stockitemtypes;

import static org.dieschnittstelle.jee.esa.shared.lib.Util.show;

import org.dieschnittstelle.jee.esa.basics.IStockItem;
import static org.dieschnittstelle.jee.esa.shared.lib.Util.*;

public class Milk implements IStockItem {

	private int units;

	private String brandname;
	
	private int price;
	
	public Milk() {
		show("Milk: constructor invoked");
	}
	
	
	@Override
	public int getUnits() {
		return this.units;
	}

	@Override
	public void initialise(int units,String brandname) {
		show("Milk: initialise() invoked");
		
		this.units = units;
		this.brandname = brandname;
	}

	@Override
	public void purchase(int unitsToPurchase) {
		
		show("Milk: purchase() invoked");
		
		if (unitsToPurchase > this.units) {
			throw new RuntimeException(
					"You cannot purchase more than what is available. Got: "
							+ unitsToPurchase
							+ " units to consume, but have available only: "
							+ this.units);
		}
		this.units -= unitsToPurchase;
	}

	@Override
	public String toString() {
		return String.format("{Milk %s %d %d}", this.brandname, this.units, this.price);
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		show("Milk: setPrice() invoked");

		this.price = price;
	}
	
	

}
