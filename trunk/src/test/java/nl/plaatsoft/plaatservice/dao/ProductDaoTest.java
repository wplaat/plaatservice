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
	 * Find by name null.
	 */
	@Test
	public void findByNameNull() {
		
        productRepository.truncate(Product.class);
	        	        	               
        Optional<Product> product =  productRepository.findByName("PlaatService", "0.5.0", "Windows10");
        assertTrue(product.isPresent()==false);   
	}
	
	/**
	 * Find test.
	 */
	@Test
	public void findAll() {
		        
        Product product1 = new Product("PlaatService", "0.3.0", "Windows10");
        productRepository.save(product1);
        
        Product product2 = new Product("PlaatService", "0.4.0", "Windows10");
        productRepository.save(product2);
        
        Product product3 = new Product("PlaatService", "0.5.0", "Windows10");
        productRepository.save(product3);
               
        List<Product> products = productRepository.findAll();
        
        assertEquals(3, products.size());        
	}
		
	/**
	 * Find by name.
	 */
	@Test
	public void findByName() {
		    
        productRepository.truncate(Product.class);
	        	        
	    Product product2 = new Product("PlaatService", "0.6.0", "Windows10");
	    productRepository.save(product2);
	        
	    Product product3 = new Product("PlaatService", "0.7.0", "Windows10");
	    productRepository.save(product3);
	               
	    Optional<Product> product =  productRepository.findByName("PlaatService", "0.6.0", "Windows10");
	    assertEquals("0.6.0", product.get().getVersion());   
	}
}
