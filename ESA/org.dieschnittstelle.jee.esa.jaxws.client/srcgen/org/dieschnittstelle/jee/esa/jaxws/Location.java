
package org.dieschnittstelle.jee.esa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für location complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="location">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="geoLat" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="geoLong" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "location", propOrder = {
    "geoLat",
    "geoLong",
    "id"
})
@XmlSeeAlso({
    Address.class
})
public class Location {

    protected long geoLat;
    protected long geoLong;
    protected long id;

    /**
     * Ruft den Wert der geoLat-Eigenschaft ab.
     * 
     */
    public long getGeoLat() {
        return geoLat;
    }

    /**
     * Legt den Wert der geoLat-Eigenschaft fest.
     * 
     */
    public void setGeoLat(long value) {
        this.geoLat = value;
    }

    /**
     * Ruft den Wert der geoLong-Eigenschaft ab.
     * 
     */
    public long getGeoLong() {
        return geoLong;
    }

    /**
     * Legt den Wert der geoLong-Eigenschaft fest.
     * 
     */
    public void setGeoLong(long value) {
        this.geoLong = value;
    }

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

}
