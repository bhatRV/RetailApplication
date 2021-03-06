/**
 * 
 */
package rv.retail.restcontroller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rv.retail.beans.ProductDetails;
import rv.retail.exception.ProductNotFoundException;
import rv.retail.manager.RetailStorageManager;

/**
 * This is RESTFul service that provides API to get the product name and price.
 * It also provides API for changing the price of a product.
 * 
 * @author Rashmi Bhat
 * @version 1.0
 *
 */
@Api(value="/rv/retail",description="Rashmi-Retail-APP REst apis.",produces ="application/json")
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/")
public class RetailRestController {
	
	@Autowired
	private RetailStorageManager retailStorageManager;
	/**
	 *  Error handling is done by this method.
	 * @param ex exception to be handled.
	 * @return ResponseEntity<ErrorResponse> This the HTTP response entity object with custom error response object.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		
		ErrorResponse error = new ErrorResponse();
		if (ex instanceof  IllegalArgumentException ){
			error.setErrorCode(HttpStatus.BAD_REQUEST.value());
			error.setMessage (ex.getMessage());
		}else if (ex instanceof ProductNotFoundException){
			error.setErrorCode(HttpStatus.NOT_FOUND.value());
			error.setMessage (ex.getMessage());
		}else{
			error.setErrorCode(500);
			error.setMessage (" Internal Server Error. Detailed error is:" +ex.getMessage());
		}
		error.setMessage (ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 *  HTTP GET method to get the product details and price for a given productId.
	 * @param productId Unique identifier for the product.
	 * @return ProductDetails  object that has the details of product.
	 * @throws Exception exception.
	 */
	@ApiOperation(value = "get Product details by its unique identifier.",
			notes = "The successful invocation of this request will give the product name and its price information")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Returned when there internal errors."),
			@ApiResponse(code = 404, message = "Returned when there is no product pre created"),
			@ApiResponse(code = 400, message = "Returned when inputs are invalid")

	})
	@RequestMapping(method=RequestMethod.GET, value="/product/{productId}")
	public ProductDetails getProductDetails(@PathVariable("productId") Long productId) throws Exception  {
		
		 if (productId == null || productId <= 0) {
	            throw new IllegalArgumentException("The Product Identifier can not be null or empty. Provide a valid product Identifier");
	     }
		ProductDetails prodDetails = retailStorageManager.getProductDetails(productId);
		 
		 if ((prodDetails.getId() == null) || (prodDetails.getId() == 0)){
	            throw new ProductNotFoundException("Invalid  Product Identifier provided. No such Product listed.");
	     }
	     System.out.println("Product details fetched successfully!");
		return prodDetails;
	}
	
	/**
	 * HTTP put method to update price
 	 * @param productId Id of the product to be updated.
	 * @param price Price to be updated.
	 * @return boolean Updated successfully or not.
	 * @throws Exception Generic exception.
	 */
	@ApiOperation(value = "update entry for Product Price details.",
			notes = "The successful invocation of this request will result in updation of the PRICE of the specified product and price informations")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Returned when there internal errors.")
	})
	@RequestMapping(method=RequestMethod.PUT, value="/product/{productId}")
	public boolean updateProductPrice(@PathVariable("productId") Long productId, @RequestBody ProductDetails price) throws Exception{
		
		if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("The Product Identifier can not be null or empty.");
		}
		System.out.println("Product details will be updated now........");

		return retailStorageManager.updateProductPrice(productId, price.getPriceDetails().getPrice());
	}

	/**
	 * HTTP POST method to create and entry for price of the product
	 * @param productId Id of the product to be added.
	 * @param price Price to be added.
	 * @throws Exception Generic exception.
	 */

	@ApiOperation(value = "create entry for Product Price details.",
			notes = "The successful invocation of this request will result in creation of the specified product and price informations")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Returned when created successfully."),
			@ApiResponse(code = 500, message = "Returned when there internal errors.")
	})
	@RequestMapping(method=RequestMethod.POST, value="/product/{productId}", consumes = "application/json")
	public ResponseEntity<Object> createProductPrice(@PathVariable("productId") Long productId, @RequestBody ProductDetails price){
		if (productId == null || productId <= 0) {
			throw new IllegalArgumentException("The Product Identifier can not be null or empty.");
		}
		 		retailStorageManager.createPriceData(productId, price.getPriceDetails().getPrice(),price.getPriceDetails().getCurrency());
		return ResponseEntity.ok().body("success");
	}


}
