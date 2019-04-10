/**
 * 
 */
package rv.retail.dao;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import rv.retail.beans.PriceDetails;
import rv.retail.dao.cas.object.PriceDO;

/**
 * This is the DAO representation for Cassandra DB.
 * 
 * @author Rashmi Bhat
 *
 */
public interface PriceRepository extends CrudRepository<PriceDO, String>{
	
	/**
	 * This method queries the database and gets the price details for the given productId.
	 * @param productId Product id to be 
	 * @return PriceDO
	 */
	
	@Query("SELECT id,currency,price FROM product_pricing WHERE id=?0")
	public PriceDO getProductPrice(Long productId);
	
	/**
	 * This method updates the price value for a given productId in the database.
	 * @param productId
	 * @param prices
	 * @return boolean
	 */
	
	@Query("UPDATE target.product_pricing SET price=?1 WHERE  id=?0 IF EXISTS")
	public boolean updatePrice(Long productId, Float prices);



	@Query("INSERT INTO target.product_pricing (id, price, currency) VALUES (?0,?1,?2)")
	public void addPrice(Long productId,Float prices,String currency);
}
