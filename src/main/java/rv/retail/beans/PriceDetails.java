/**
 * 
 */
package rv.retail.beans;

/**
 * This class represents the price and the currency of the product.
 * @author Rashmi Bhat
 *
 */
public class PriceDetails {

	private Float price;
	private String currency;
	
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}
