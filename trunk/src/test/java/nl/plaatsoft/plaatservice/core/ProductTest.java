package nl.plaatsoft.plaatservice.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The Class ProductTest.
 */
public class ProductTest {
	
	/**
	 * Check product json.
	 */
	@Test
	public void checkProductJson() {
		
		Products product = new Products();

		assertEquals(654, product.getItems().length());
	}
}
