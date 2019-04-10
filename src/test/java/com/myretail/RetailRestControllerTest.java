/**
 * 
 */
package com.myretail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import rv.retail.Application;
import rv.retail.beans.ProductDetails;
import rv.retail.manager.RetailStorageManager;
import rv.retail.beans.PriceDetails;
import rv.retail.restcontroller.RetailRestController;

/**
 * @author Rashmi Bhat
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest()
@SpringBootTest(classes={Application.class})
public class RetailRestControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private RetailStorageManager retailStorageManager;
	public static Long id = 12312313l;
	
	@Test
	public void getProductDetailsTest() throws Exception{
		
		ProductDetails productDetails = new ProductDetails();
		productDetails.setName("MyProduct");
		productDetails.setId(id);
		PriceDetails cp = new PriceDetails();
		cp.setCurrency("USD");
		cp.setPrice(35.5f);
		productDetails.setPriceDetails(cp);
		Mockito.when(retailStorageManager.getProductDetails(id)).thenReturn(productDetails);
		
		RequestBuilder rb = MockMvcRequestBuilders.get("/product/12312313").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(rb).andReturn();
		
		System.out.println("-----"+ result.getResponse().getContentAsString());
		
		String expected = "{id:13860428,name:'Game of Thrones',currentPrice:{price:35.5,currency:USD}}";
		
		JSONAssert.assertEquals(expected,result.getResponse().getContentAsString(), false);
	}
}
