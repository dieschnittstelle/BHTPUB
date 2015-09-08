package org.dieschnittstelle.jee.esa.crm.ejbs;

public class ShoppingException extends /*Exception*/ RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 205789836734861691L;

	public static enum ShoppingSessionExceptionReason {
		CAMPAIGN_INVALID, STOCK_EXCEEDED, INCOMPLETE_SESSION_DATA, TECHNICAL_EXCEPTION, UNKNOWN;  
	}
	
	private ShoppingSessionExceptionReason reason;
	
	
	public ShoppingException() {
		super();
		this.setReason(ShoppingSessionExceptionReason.UNKNOWN);
	}
	
	public ShoppingException(Throwable cause) {
		super(cause);
		this.setReason(ShoppingSessionExceptionReason.TECHNICAL_EXCEPTION);
	}
	
	public ShoppingException(String msg) {
		super(msg);
		this.setReason(ShoppingSessionExceptionReason.UNKNOWN);
	}
	
	public ShoppingException(String msg,Throwable cause) {
		super(msg,cause);
		this.setReason(ShoppingSessionExceptionReason.TECHNICAL_EXCEPTION);
	}

	public ShoppingException(ShoppingSessionExceptionReason reason) {
		super();
		this.setReason(reason);
	}

	public ShoppingException(ShoppingSessionExceptionReason reason, String msg) {
		super(msg);
		this.setReason(reason);
	}

	public ShoppingException(ShoppingSessionExceptionReason reason, Throwable cause) {
		super(cause);
		this.setReason(reason);
	}

	public ShoppingException(ShoppingSessionExceptionReason reason, String msg,Throwable cause) {
		super(msg,cause);
		this.setReason(reason);
	}

	public ShoppingSessionExceptionReason getReason() {
		return reason;
	}

	public void setReason(ShoppingSessionExceptionReason reason) {
		this.reason = reason;
	}
	
}
