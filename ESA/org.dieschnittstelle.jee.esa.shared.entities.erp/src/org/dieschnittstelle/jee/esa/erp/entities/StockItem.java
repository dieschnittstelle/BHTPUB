package org.dieschnittstelle.jee.esa.erp.entities;

import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.jboss.logging.Logger;

//@Entity
@Table(name = "stock")
@IdClass(ProductAtPosPK.class)
public class StockItem {

	protected static Logger logger = Logger.getLogger(StockItem.class);

	@Id
	@ManyToOne
	private PointOfSale pos;

	@Id
	@ManyToOne
	private IndividualisedProductItem product;

	private int price;
	
	private int units;

	public StockItem() {

	}

	public StockItem(IndividualisedProductItem product,
			PointOfSale pos, int units) {
		this.product = product;
		this.pos = pos;
		this.units = units;
	}

	public PointOfSale getPos() {
		return pos;
	}

	public void setPos(PointOfSale pos) {
		this.pos = pos;
	}

	public IndividualisedProductItem getProduct() {
		return product;
	}

	public void setProduct(IndividualisedProductItem product) {
		this.product = product;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	/*
	 * the lifecycle log messages
	 */

	@PostLoad
	public void onPostLoad() {
		logger.info("onPostLoad(): " + this);
	}

	@PostPersist
	public void onPostPersist() {
		logger.info("onPostPersist(): " + this);
	}

	@PostRemove
	public void onPostRemove() {
		logger.info("onPostRemove(): " + this);
	}

	@PostUpdate
	public void onPostUpdate() {
		logger.info("onPostUpdate(): " + this);
	}

	@PrePersist
	public void onPrePersist() {
		logger.info("onPrePersist(): " + this);
	}

	@PreRemove
	public void onPreRemove() {
		logger.info("onPreRemove(): " + this);
	}

	@PreUpdate
	public void onPreUpdate() {
		logger.info("onPreUpdate(): " + this);
	}

	public String toString() {
		return "{StockItemEntity " + this.price + ", " + this.product + "@"
				+ this.pos + "}";
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

}
