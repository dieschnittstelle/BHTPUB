package org.dieschnittstelle.jee.esa.basics.reflection.stockitemtypes;

import org.dieschnittstelle.jee.esa.basics.IStockItem;

import static org.dieschnittstelle.jee.esa.shared.lib.Util.*;		

public class Chocolate implements IStockItem {

	private int units;
	
	private String brandname;
	
	private int price;
	
	public Chocolate() {
		show("Chocolate: constructor invoked");
	}
	
	public Chocolate(String brandname) {
		this.brandname = brandname;
	}
	
	
	@Override
	public void initialise(int units,String brandname) {
		show("Chocolate: intialise() invoked");

		this.units = units;
		this.brandname = brandname;
	}

	@Override
	public void purchase(int unitsToPurchase) {
		
		show("Chocolate: purchase() invoked");
		
		if (unitsToPurchase > this.units) {
			throw new RuntimeException(
					"You cannot purchase more than what is available. Got: "
							+ unitsToPurchase
							+ " units to purchase, but have available only: "
							+ this.units);
		}
		this.units -= unitsToPurchase;
	}

	@Override
	public int getUnits() {
		return this.units;
	}
	
	@Override
	public String toString() {
		return String.format("{Chocolate %s %d %d}", this.brandname, this.units, this.price);
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
		show("Chocolate: setPrice() invoked");

		this.price = price;
	}

}
