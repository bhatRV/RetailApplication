/**
 * 
 */
package rv.retail.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Makes HTTP call to fetch data from external system via REST GET
 * External server details are configured with application.properties
 * 
 * @author Rashmi Bhat
 *
 */
@Service
@PropertySource("classpath:application.properties")
public class ExtProdDetailsService {

	@Value("${endpoint.url}")
	private StringBuffer baseURI;
	 @Value("${endpoint.excludes}")
	private StringBuffer excludes;
	
	public static final String PRODUCT="product";
	public static final String ITEM="item";
	public static final String PRODUCT_DESCRIPTION="product_description";
	public static final String TITLE="title";

	private RestTemplate restTemplate = new RestTemplate();
	
	/**
	 * This fetches data from Target URL using REST GET call
	 * 
	 * @param id
	 * @return ProductServiceObjImpl
	 * @throws JSONException
	 */
	public ProductServiceObjImpl getProductDetails(Long id) throws JSONException {
		//String productJson = restTemplate.getForObject(baseURI+id, String.class);
		StringBuffer URL = new StringBuffer(baseURI);
		URL.append(id);
		URL.append(excludes);

		String jsonResponse =restTemplate.getForObject(String.valueOf(URL),String.class);
		System.out.println("Product Json response from external URL is : "+jsonResponse);
		ProductServiceObjImpl productServiceObjImpl = extractProductName(jsonResponse);
		return productServiceObjImpl;
	}
	
	public ProductServiceObjImpl extractProductName(String productJson) throws JSONException{
		System.out.println("extracting ProductName from ext JSON...... ");
		ProductServiceObjImpl productServiceObjImpl = new ProductServiceObjImpl();

		JSONObject obj = new JSONObject(productJson);
		JSONObject product = obj.getJSONObject(PRODUCT);
		JSONObject item = product.getJSONObject(ITEM);
		JSONObject productDescription = item.getJSONObject(PRODUCT_DESCRIPTION);
		productServiceObjImpl.setName(productDescription.getString(TITLE));
		return productServiceObjImpl;
	}
	
}
