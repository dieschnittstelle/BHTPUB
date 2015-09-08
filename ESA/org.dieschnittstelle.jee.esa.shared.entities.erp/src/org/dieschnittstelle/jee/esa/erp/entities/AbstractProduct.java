package org.dieschnittstelle.jee.esa.erp.entities;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.dieschnittstelle.jee.esa.entities.GenericCRUDEntity;

/*
 * UE JRS3: entfernen Sie die Auskommentierung der Annotation
 */
//@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public abstract class AbstractProduct implements Serializable, GenericCRUDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6940403029597060153L;

	private long id;

	private String name;
	
	private int price;

	public AbstractProduct() {

	}

	public AbstractProduct(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
