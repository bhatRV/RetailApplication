/**
 * 
 */
package rv.retail.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

import rv.retail.beans.ProductDetails;
import rv.retail.dao.PriceRepository;
import rv.retail.beans.PriceDetails;
import rv.retail.dao.cas.object.PriceDO;
import rv.retail.exception.ProductNotFoundException;
import rv.retail.service.ExtProdDetailsService;
import rv.retail.service.ProductServiceObjImpl;

/**
 * This class is Will Perform  the DB operations.
 * 
 * @author Rashmi Bhat
 *
 */
@Component
public class RetailStorageManager {

	@Autowired
	private ExtProdDetailsService extProdDetailsService;
	@Autowired
	private PriceRepository priceRepository;
	
	/**
	 * This method calls price repository and gets the price data from the database.
	 * It also calls product service to fetch the product name.
	 * It merges the data from both the data sources and provides ProductBO object.
	 * 
	 * @param productId Get details of the product identified by this parameter.
	 * @return ProductBO ProductBO
	 * @throws Exception Generic Exception
	 */
	public ProductDetails getProductDetails(Long productId) throws Exception {
		PriceDO priceDO = priceRepository.getProductPrice(productId);
		ProductServiceObjImpl productServiceObjImpl = extProdDetailsService.getProductDetails(productId);
		return populateProductDetails(priceDO, productServiceObjImpl);
	}
	
	/**
	 * This method calls the DAO layer method to update the price of given productId.
	 * 
	 * @param productId Product id to update
	 * @param price Price to update
	 * @return boolean Updated successfully or not.
	 * @throws Exception Generic exception.
	 */
	public boolean updateProductPrice(Long productId, Float price) throws Exception{
		return priceRepository.updatePrice(productId, price);
	}

	/**
	 * This method performs merge logic on PriceDO and ProductServiceObjImpl and returns ProductBO.
	 * @param  price  price details for the product.
	 * @param productServiceObjImpl provides product name.
	 * @return ProductBO Business object that is integrated from DO and SO.
	 */
	public ProductDetails populateProductDetails(PriceDO price , ProductServiceObjImpl productServiceObjImpl) throws ProductNotFoundException {

		if(productServiceObjImpl!=null && price!=null)
		{
		ProductDetails productDetails = new ProductDetails();
		productDetails.setName(productServiceObjImpl.getName());

			productDetails.setId(price.getProductId());
			PriceDetails priceDetails = new PriceDetails();
			priceDetails.setCurrency(price.getCurrency());
			priceDetails.setPrice(price.getPrice());
			productDetails.setPriceDetails(priceDetails);
			return productDetails;

		}
		else
		{
			throw (price==null? new ProductNotFoundException("No Price Details found for this product Id"): new ProductNotFoundException("No Product Details found for this product Id"));
		}
	}

	/**
	 * This method calls the DAO layer method to create the price for the given productId.
	 *
	 * @param productId Product id to be created
	 * @param price price of the product
	 * @param currency
	 * @throws Exception Generic exception.
	 */

	public void createPriceData(Long productId, Float price,String currency) {
		 priceRepository.addPrice(productId,price,currency);
	}
}
