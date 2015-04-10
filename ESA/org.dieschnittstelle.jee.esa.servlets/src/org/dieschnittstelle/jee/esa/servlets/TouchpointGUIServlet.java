package org.dieschnittstelle.jee.esa.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.crm.entities.Address;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;

public class TouchpointGUIServlet extends HttpServlet {

	protected static Logger logger = Logger
			.getLogger(TouchpointGUIServlet.class);

	public TouchpointGUIServlet() {
		System.err.println("TouchpointGUIServlet: constructor invoked\n");
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println("TouchpointGUIServlet: doGet() invoked\n");

		displayView(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		
		System.err.println("TouchpointGUIServlet: doPost() invoked\n");
		
		// obtain the executor
		TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
				.getAttribute("touchpointCRUD");

		// obtain the pathInfo value
		String pathInfo = request.getPathInfo();

		// check whether we need to create or delete a touchpoint
		if (pathInfo.startsWith("/delete")) {
			// if we have a delete request, the id to be deleted will be the
			// last segment of the path
			int id = Integer.parseInt(pathInfo.substring(pathInfo
					.lastIndexOf("/") + 1));
			exec.deleteTouchpoint(id);
		} else if (request.getPathInfo().startsWith("/create")) {
			String name = request.getParameter("name");

			// if we have a create request, we read out the parameters
			String street = request.getParameter("street");
			String houseNr = request.getParameter("houseNr");
			String city = request.getParameter("city");
			String zipCode = request.getParameter("zipCode");

			// create a new address
			Address addr = new Address(street, houseNr, zipCode, city);
			StationaryTouchpoint tp = new StationaryTouchpoint(-1, name, addr);
			exec.createTouchpoint(tp);
		}

		displayView(request, response);
	}

	/**
	 * display the view (we always use the same jsp for all requests)
	 */
	/*
	 * now pass the request to the jsp for display
	 */
	private void displayView(HttpServletRequest request,
			HttpServletResponse response) {
		/*
		 * we read out the touchpoint data and add it as an attribute to the
		 * request such that the jsp can be agnostic with regard to how the data
		 * to be display has been obtained
		 */
		// obtain the executor
		TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
				.getAttribute("touchpointCRUD");
		// read out the touchpoints and them to the request
		request.setAttribute("touchpoints", exec.readAllTouchpoints());

		// obtain a dispatcher for the jsp (which will always be the same view)
		RequestDispatcher dispatcher = ((HttpServletRequest) request)
				.getServletContext().getRequestDispatcher("/gui.jsp");
		// and forward the request to the jsp
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}
	}

}
