package nl.plaatsoft.plaatservice.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

/**
 * The Class ProductDaoTest.
 * 
 * @author wplaat
 */
public class ProductDaoTest {

	/**
	 * Find test.
	 */
	@Test
	public void findAll() {
		
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PlaatService");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        ProductDao productRepository = new ProductDao(entityManager);
        
        Product product1 = new Product("PlaatService", "0.3.0", "Windows10");
        productRepository.save(product1);
        
        Product product2 = new Product("PlaatService", "0.4.0", "Windows10");
        productRepository.save(product2);
        
        Product product3 = new Product("PlaatService", "0.5.0", "Windows10");
        productRepository.save(product3);
               
        List<Product> products = productRepository.findAll();
        
        assertEquals(3, products.size());        
	}
		
	@Test
	public void findByName() {
		
		 EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PlaatService");
	        EntityManager entityManager = entityManagerFactory.createEntityManager();
	        
	        ProductDao productRepository = new ProductDao(entityManager);
	        
	        Product product1 = new Product("PlaatService", "0.3.0", "Windows10");
	        productRepository.save(product1);
	        
	        Product product2 = new Product("PlaatService", "0.4.0", "Windows10");
	        productRepository.save(product2);
	        
	        Product product3 = new Product("PlaatService", "0.5.0", "Windows10");
	        productRepository.save(product3);
	               
	        List<Product> products =  productRepository.findByName("PlaatService", "0.5.0", "Windows10");
	        assertEquals(1, products.size());   
	}
}
