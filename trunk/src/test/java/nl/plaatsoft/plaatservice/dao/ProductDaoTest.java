package nl.plaatsoft.plaatservice.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

/**
 * The Class ProductDaoTest.
 * 
 * @author wplaat
 */
public class ProductDaoTest {

	/** The product repository. */
	private ProductDao productRepository;
	
	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		 EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PlaatService");
	     EntityManager entityManager = entityManagerFactory.createEntityManager();	       
	     productRepository = new ProductDao(entityManager);
	}
		
	/**
	 * Find All.
	 */
	@Test
	public void findAll() {
		        
        Product product1 = new Product("PlaatService", "0.1.0", "Windows10");
        productRepository.save(product1);
        
        Product product2 = new Product("PlaatService", "0.2.0", "Windows10");
        productRepository.save(product2);
        
        Product product3 = new Product("PlaatService", "0.3.0", "Windows10");
        productRepository.save(product3);
               
        List<Product> products = productRepository.findAll();
        
        assertEquals(3, products.size());        
	}
		
	/**
	 * Find by name.
	 */
	@Test
	public void findByName() {
		    	        	        
	    Product product2 = new Product("PlaatService", "0.4.0", "Windows10");
	    productRepository.save(product2);
	        
	    Product product3 = new Product("PlaatService", "0.5.0", "Windows10");
	    productRepository.save(product3);
	               
	    Optional<Product> product =  productRepository.findByName("PlaatService", "0.4.0", "Windows10");
	    assertEquals("0.4.0", product.get().getVersion());   
	}
	
	
	/**
	 * Find by name not found
	 */
	@Test
	public void findByNameNotFound () {
		    	        	        
	    Product product2 = new Product("PlaatService", "0.4.0", "Windows10");
	    productRepository.save(product2);
	        
	    Product product3 = new Product("PlaatService", "0.5.0", "Windows10");
	    productRepository.save(product3);
	               
	    // New entry is created
	    Optional<Product> product =  productRepository.findByName("PlaatService", "0.6.0", "Windows10");
	    assertTrue(product.isPresent()==true);   
	    assertEquals("3", product.get().getId().toString());  
	}
	
	/**
	 * Find by name null.
	 */
	@Test
	public void findByNameNull() {
			        	        	               
        Optional<Product> product =  productRepository.findByName("PlaatService", "0.6.0", "Windows10");
        assertEquals("1", product.get().getId().toString());   
	}
}
