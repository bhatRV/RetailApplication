/**
 * 
 */
package rv.retail.beans;

/**
 * This is Object that represents the Complete product.
 * 
 * @author Rashmi Bhat
 *
 */
public class ProductDetails {

	private Long id;
	private String name;
	private PriceDetails priceDetails;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PriceDetails getPriceDetails() {
		return priceDetails;
	}
	public void setPriceDetails(PriceDetails priceDetails) {
		this.priceDetails = priceDetails;
	}
}
