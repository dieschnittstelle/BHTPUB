package org.dieschnittstelle.jee.esa.jaxrs.client.junit;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.jaxrs.IProductCRUDWebService;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class ProductCRUDClient {

	private IProductCRUDWebService proxy;
	
	protected static Logger logger = Logger.getLogger(ProductCRUDClient.class);

	public ProductCRUDClient() throws Exception {
		/*
		 * register jackson as reader and writer for json
		 */
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		// the MessageBodyReader must be added explicitly to the provider factory. Here, we use the jackson
		// implementation
		ResteasyProviderFactory.getInstance().addMessageBodyReader(JacksonJaxbJsonProvider.class);
		ResteasyProviderFactory.getInstance().addMessageBodyWriter(JacksonJaxbJsonProvider.class);

		/*
		 * create a client for the web service passing the interface
		 */
		proxy = ProxyFactory.create(IProductCRUDWebService.class,
				"http://localhost:8888/org.dieschnittstelle.jee.esa.jaxrs", new ApacheHttpClient4Executor());
	}

	public AbstractProduct createProduct(IndividualisedProductItem prod) {
		AbstractProduct created = proxy.createProduct(prod);
		// as a side-effect we set the id of the created product on the argument before returning
		prod.setId(created.getId());
		return created;
	}

	public List<?> readAllProducts() {
		return proxy.readAllProducts();
	}

	public AbstractProduct updateProduct(AbstractProduct update) {
		return proxy.updateProduct(update.getId(),(IndividualisedProductItem)update);
	}

	public boolean deleteProduct(long id) {
		return proxy.deleteProduct(id);
	}

	public AbstractProduct readProduct(long id) {
		return proxy.readProduct(id);
	}

}
