package nl.plaatsoft.plaatservice.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

/**
 * The Class ProductDaoTest.
 * 
 * @author wplaat
 */
public class ProductDaoTest extends GeneralDaoTest {
	
	/**
	 * Find By Id.
	 */
	@Test
	public void findById() {
		        						
		productDao.save(new Product("PlaatService", "0.1.0", "Windows10"));
               
        Product product = productDao.findById(1).get();
        
        assertEquals(1, product.getPid());        
	}
	
	
	/**
	 * Find All.
	 */
	@Test
	public void findAll() {
		        		
        productDao.save(new Product("PlaatService", "0.2.0", "Windows10"));
        productDao.save(new Product("PlaatService", "0.3.0", "Windows10"));
        productDao.save(new Product("PlaatService", "0.4.0", "Windows10"));
               
        List<Product> products = productDao.findAll();
        
        assertEquals(3, products.size());        
	}
		
	/**
	 * Find by name 1.
	 */
	@Test
	public void findByName1() {
		    	        	   		
	    productDao.save(new Product("PlaatService", "0.5.0", "Windows10"));
	    productDao.save(new Product("PlaatService", "0.6.0", "Windows10"));
	               
	    Optional<Product> product =  productDao.findByName("PlaatService", "0.7.0", "Windows10");
	    assertEquals("0.7.0", product.get().getVersion());   
	}
	
	
	
	/**
	 * Find by name 2.
	 */
	@Test
	public void findByName2() {
		    	 		
	    productDao.save(new Product("PlaatService", "0.7.0", "Windows10"));
	    productDao.save(new Product("PlaatService", "0.8.0", "Windows10"));
	    productDao.save(new Product("PlaatService", "0.9.0", "Windows10"));
	    productDao.save(new Product("PlaatService", "1.0.0", "Windows10"));
	    productDao.save(new Product("PlaatService", "1.1.0", "Windows10"));
	    productDao.save(new Product("PlaatService", "1.2.0", "Windows10"));
	               
	    Optional<Product> product =  productDao.findByName("PlaatService");
	    assertEquals("1.2.0", product.get().getVersion());   
	}
	
	
	/**
	 * Find by name not found
	 */
	@Test
	public void findByNameNotFound () {
		    	        	        		
	    productDao.save(new Product("PlaatService", "1.3.0", "Windows10"));
	    productDao.save(new Product("PlaatService", "1.4.0", "Windows10"));
	               
	    // New entry is created
	    Optional<Product> product =  productDao.findByName("PlaatService", "1.4.0", "Windows10");
	    assertTrue(product.isPresent()==true);   
	    assertEquals("1.4.0", product.get().getVersion().toString());  
	}
	
	/**
	 * Find by name null.
	 */
	@Test
	public void findByNameNull() {
			        	        	               
        Optional<Product> product =  productDao.findByName("PlaatService", "1.5.0", "Windows10");
        assertEquals("1.5.0", product.get().getVersion().toString());   
	}
}
