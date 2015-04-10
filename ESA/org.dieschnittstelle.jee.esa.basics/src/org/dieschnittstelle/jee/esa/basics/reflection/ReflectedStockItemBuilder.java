package org.dieschnittstelle.jee.esa.basics.reflection;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.basics.IStockItem;
import org.dieschnittstelle.jee.esa.basics.IStockItemBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

// this builder only reads in the 
public class ReflectedStockItemBuilder implements IStockItemBuilder {

	protected static Logger logger = Logger
			.getLogger(ReflectedStockItemBuilder.class);

	@Override
	public IStockItem buildStockItemFromElement(Element el) {

		try {
			// obtain the child nodes
			NodeList children = el.getChildNodes();
			// iterate over the nodes and populate a map of attributes that we will use later for instantiating the java objects
			Map<String, String> instanceAttributes = new HashMap<String, String>();

			for (int i = 0; i < children.getLength(); i++) {
				// once we have found an element node we check its name
				if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
					// get the textual content and the name
					String elementContent = ((Element) children.item(i))
							.getTextContent();
					String elementName = ((Element) children.item(i))
							.getTagName();
					logger.debug("found element " + elementName
							+ " with content: " + elementContent);
					instanceAttributes.put(elementName, elementContent);
				} else {
					// logger.debug("found node " + children.item(i)
					// + " of class " + children.item(i).getClass());
				}
			}
			

			
			logger.info("read out child elements and values: " + instanceAttributes);

			// try to obtain the class given the classname and create an
			// instance of it
			Class<?> klass = Class.forName(instanceAttributes.get("class"));
			IStockItem instance = (IStockItem) klass.newInstance();

//			for (Field field : klass.getDeclaredFields()) {
//				System.err.println("found field: " + field.getClass() + " of name " + field.getName());
//			}
//			
//			for (Method method : klass.getDeclaredMethods()) {
//				System.err.println("found method: " + method.getClass() + " of name " + method.getName());
//			}
						
			instance.initialise(Integer.parseInt(instanceAttributes.get("units")),
					instanceAttributes.get("brandname"));
			
			/**
			 * fuegen Sie hier die Erweiterungen fuer Uebungsaufgabe BAS1 ein
			 */
			
			instanceAttributes.remove("class");
			instanceAttributes.remove("brandname");
			instanceAttributes.remove("units");

			for (String key : instanceAttributes.keySet()) {
				// determine the name of the setter
				String setterName = "set" + key.substring(0,1).toUpperCase() + key.substring(1);
				Method setterMethod = null;
				// iterate over the methods
				for (Method setter: klass.getDeclaredMethods()) {
					if (setterName.equals(setter.getName())) {
						setterMethod = setter;
					}
				}
				
				logger.info("found setter: " + setterMethod);
				// check the type
				Class<?> type = setterMethod.getParameterTypes()[0];
				if (type != java.lang.String.class) {
					if (type == Integer.TYPE) {
						setterMethod.invoke(instance,Integer.parseInt(instanceAttributes.get(key)));
					}
					else {
						logger.error("cannot handle type: " + type);
					}
				}
				else {
					setterMethod.invoke(instance,instanceAttributes.get(key));
				}
			}
			
			// and pass back the instance
			return (IStockItem)instance;
		} catch (ClassNotFoundException e) {
			logger.error("got ClassNotFoundException: " + e, e);
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			logger.error("got InstantiationException: " + e, e);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			logger.error("got IllegalAccessException: " + e, e);
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("got Exception: " + e, e);
			throw new RuntimeException(e);		
		} 

	}
	
	/*
	 * create getter/setter names
	 */
	public static String getAccessorNameForField(String accessor,String fieldName) {
		return accessor + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1); 
	}


}
