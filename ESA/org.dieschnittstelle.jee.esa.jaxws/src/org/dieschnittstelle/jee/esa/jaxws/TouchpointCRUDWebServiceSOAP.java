package org.dieschnittstelle.jee.esa.jaxws;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.dieschnittstelle.jee.esa.entities.GenericCRUDExecutor;
import org.jboss.logging.Logger;

import static org.dieschnittstelle.jee.esa.shared.lib.Util.*;

@WebService(targetNamespace = "http://dieschnittstelle.org/jee/esa/jaxws", serviceName = "TouchpointCRUDWebService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class TouchpointCRUDWebServiceSOAP {

	protected static Logger logger = Logger
			.getLogger(TouchpointCRUDWebServiceSOAP.class);

	@Resource
	private WebServiceContext wscontext;

	public TouchpointCRUDWebServiceSOAP() {
		logger.info("<constructor>");
	}

	@PostConstruct
	@WebMethod(exclude = true)
	public void initialiseContext() {
		logger.info("@PostConstruct: the wscontext is: " + wscontext);

		// we cannot read out any context attributes (ServletContext,
		// HttpServletRequest) from the WebServiceContext as this is only
		// allowed from a thread that actually handles a particular request to a
		// service operation
		// wscontext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
	}

	@WebMethod
	public List<AbstractTouchpoint> readAllTouchpoints() {
		logger.info("readAllTouchpoints()");

		logger.info("readAllTouchpoints(): I am: " + this);
		
		// we obtain the servlet context from the wscontext
		ServletContext ctx = (ServletContext) wscontext.getMessageContext()
				.get(MessageContext.SERVLET_CONTEXT);
		logger.info("readAllTouchpoints(): servlet context is: " + ctx);
		// we also read out the http request
		HttpServletRequest httpRequest = (HttpServletRequest) wscontext
				.getMessageContext().get(MessageContext.SERVLET_REQUEST);
		logger.info("readAllTouchpoints(): servlet request is: " + httpRequest);

		GenericCRUDExecutor<AbstractTouchpoint> touchpointCRUD = (GenericCRUDExecutor<AbstractTouchpoint>) ctx
				.getAttribute("touchpointCRUD");
		logger.info("readAllTouchpoints(): read touchpointCRUD from servletContext: "
				+ touchpointCRUD);
		
		// check that more than one requests is handled by the same instance of this class simulataneously
//		try {
//			Thread.sleep(30000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		return touchpointCRUD.readAllObjects();
	}

	@WebMethod
	public AbstractTouchpoint createTouchpoint(AbstractTouchpoint touchpoint) {
		// obtain the CRUD executor from the servlet context
		GenericCRUDExecutor<AbstractTouchpoint> touchpointCRUD = (GenericCRUDExecutor<AbstractTouchpoint>) ((ServletContext) wscontext
				.getMessageContext().get(MessageContext.SERVLET_CONTEXT))
				.getAttribute("touchpointCRUD");

		return (StationaryTouchpoint) touchpointCRUD
				.createObject(touchpoint);
	}

	@WebMethod
	public boolean deleteTouchpoint(long id) {
		// obtain the CRUD executor from the servlet context
		GenericCRUDExecutor<AbstractTouchpoint> touchpointCRUD = (GenericCRUDExecutor<AbstractTouchpoint>) ((ServletContext) wscontext
				.getMessageContext().get(MessageContext.SERVLET_CONTEXT))
				.getAttribute("touchpointCRUD");

		return touchpointCRUD.deleteObject(id);
	}
	
	/*
	 * UE JWS3: erweitern Sie den Service
	 */

}
