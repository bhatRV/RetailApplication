package rv.retail.exception;

/**
 * @author Rashmi Bhat
 *
 */
@SuppressWarnings("serial")
public class ProductNotFoundException extends Exception {
	
	public ProductNotFoundException(String message)
	{
		super(message);
	}

}
