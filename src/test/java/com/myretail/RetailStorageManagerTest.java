/**
 * 
 */
package com.myretail;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rv.retail.Application;
import rv.retail.beans.ProductDetails;
import rv.retail.dao.PriceRepository;
import rv.retail.dao.cas.object.PriceDO;
import rv.retail.exception.ProductNotFoundException;
import rv.retail.manager.RetailStorageManager;
import rv.retail.service.ExtProdDetailsService;
import rv.retail.service.ProductServiceObjImpl;

/**
 * @author Rashmi Bhat
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class RetailStorageManagerTest {

	@InjectMocks private RetailStorageManager retailStorageManager;
	@Mock private PriceRepository priceRepoitory;
	@Mock private ExtProdDetailsService extProdDetailsService;
	public static Long id = 112255331l;
	
	@Test
	public void getProductDetailsTest() throws Exception{
		PriceDO priceDO = new PriceDO();
		priceDO.setCurrency("AUD");
		priceDO.setPrice(12.3f);
		priceDO.setProductId(id);
		ProductServiceObjImpl productServiceObjImpl = new ProductServiceObjImpl();
		productServiceObjImpl.setName("ProductA");
		System.out.println("Now set Product details: ");
		Mockito.when(extProdDetailsService.getProductDetails(id)).thenReturn(productServiceObjImpl);
 		Mockito.when(priceRepoitory.getProductPrice(id)).thenReturn(priceDO);
		ProductDetails productDetails= retailStorageManager.getProductDetails(id);
		assertEquals(priceDO.getProductId(), productDetails.getId());
		assertEquals(priceDO.getCurrency(), productDetails.getPriceDetails().getCurrency());
		assertEquals(priceDO.getPrice(), productDetails.getPriceDetails().getPrice());
		assertEquals(productServiceObjImpl.getName(),productDetails.getName());
		
	}
	
	@Test
	public void populateProductDetailsTest() throws ProductNotFoundException {
		PriceDO priceDO = new PriceDO();
		priceDO.setCurrency("USD");
		priceDO.setPrice(121.2f);
		priceDO.setProductId(id);
		ProductServiceObjImpl productServiceObjImpl = new ProductServiceObjImpl();
		productServiceObjImpl.setName("ProductName4");
		
		ProductDetails productDetail = retailStorageManager.populateProductDetails(priceDO, productServiceObjImpl);
		
		assertEquals(priceDO.getProductId(), productDetail.getId());
		assertEquals(priceDO.getCurrency(), productDetail.getPriceDetails().getCurrency());
		assertEquals(priceDO.getPrice(), productDetail.getPriceDetails().getPrice());
		assertEquals(productServiceObjImpl.getName(),productDetail.getName());
		
	}
}
