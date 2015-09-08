package org.dieschnittstelle.jee.esa.servlets;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Address;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;

/**
 * partial CRUD operations for AbstractTouchpoint objects which are read in /
 * written to a file
 * 
 * @author kreutel
 * 
 */
public class TouchpointCRUDExecutor {

	/**
	 * the logger
	 */
	protected static Logger logger = Logger
			.getLogger(TouchpointCRUDExecutor.class);

	/**
	 * the id counter
	 */
	private int currentTouchpointId;

	/**
	 * the file that contains the "database"
	 */
	private File touchpointsDatabaseFile;

	/**
	 * the list of touchpoints that is managed by this class
	 */
	private List<AbstractTouchpoint> touchpoints = new ArrayList<AbstractTouchpoint>();

	/**
	 * create the executor passing a file
	 */
	public TouchpointCRUDExecutor(File databaseFile) {
		logger.info("<constructor>: " + databaseFile);
		this.touchpointsDatabaseFile = databaseFile;
	}

	/**
	 * create a touchpoint
	 */
	public AbstractTouchpoint createTouchpoint(AbstractTouchpoint touchpoint) {
		logger.info("createTouchpoint(): " + touchpoint);

		// assign an id and add it to the list
		touchpoint.setId(currentTouchpointId++);
		this.touchpoints.add(touchpoint);

		return touchpoint;
	}

	/**
	 * read all touchpoints
	 */
	public List<AbstractTouchpoint> readAllTouchpoints() {
		logger.info("readAllTouchpoints(): " + this.touchpoints);
		
		return this.touchpoints;
	}

	/**
	 * delete a touchpoint given its id
	 */
	public boolean deleteTouchpoint(final long toDeleteId) {
		logger.info("deleteTouchpoint(): " + toDeleteId);
		
		return this.touchpoints.remove(new AbstractTouchpoint() {

			@Override
			public long getId() {
				return toDeleteId;
			}

		});
	}

	/**
	 * load the data from the file
	 */
	public void load() {
		logger.info("load()...");

		try {
			if (!this.touchpointsDatabaseFile.exists()) {
				logger.warn("the file "
						+ this.touchpointsDatabaseFile
						+ " does not exist yet. Will not try to read anything, but create a default touchpoint list");
				// create an address
				Address addr = new Address("Leopoldplatz", "8", "13353",
						"Berlin");
				// create the touchpoint
				StationaryTouchpoint tp = new StationaryTouchpoint(-1,
						"Filiale Wedding", addr);

				this.createTouchpoint(tp);
			} else {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(this.touchpointsDatabaseFile));

				// first we try to read the currentTouchpointId
				this.currentTouchpointId = ois.readInt();
				logger.info("load(): read touchpointId: " + currentTouchpointId);

				// then try to read the objects
				AbstractTouchpoint obj = null;
				do {
					obj = (AbstractTouchpoint) ois.readObject();
					logger.info("load(): read object: " + obj);

					if (obj != null) {
						this.touchpoints.add(obj);
					}
				} while (true);
			}
		} catch (EOFException eof) {
			logger.info("we have reached the end of the data file");
		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}
		
		logger.info("load(): touchpoints are: " + touchpoints);
	}

	/**
	 * store the data to the file
	 */
	public void store() {
		logger.info("store()...");

		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(this.touchpointsDatabaseFile));

			// write the currentTouchpointId
			oos.writeInt(this.currentTouchpointId);
			// then write the touchpoints
			for (AbstractTouchpoint tp : this.touchpoints) {
				oos.writeObject(tp);
			}
		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}

		logger.info("store(): done.");
	}

}
