/**
 * 
 */
package com.myretail;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rv.retail.service.ExtProdDetailsService;
import rv.retail.service.ProductServiceObjImpl;

/**
 * @author Rashmi Bhat
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={ExtProdDetailsService.class})
public class ProductServiceTest {
	
	@Autowired
	ExtProdDetailsService extProdDetailsService;
	public static String jsonString = "{\"product\": {\"item\": {\"product_description\": {\"title\": \"Game of Thrones\"}}}}";
	
	@Test
	public void extractProductNameTest() throws JSONException{
		ProductServiceObjImpl productServiceObjImpl = extProdDetailsService.extractProductName(jsonString);
		assertEquals("Game of Thrones", productServiceObjImpl.getName());
	}

}
