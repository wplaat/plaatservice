package nl.plaatsoft.plaatservice.dao;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.plaatsoft.plaatservice.model.Product;

public class ProductDao {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( ProductDao.class);
	
    private EntityManager entityManager;
    
    public ProductDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
        
    public List<Product> findAll() {
        return entityManager.createQuery("from Product").getResultList();
    }
   
    public Optional<Product> save(Product Product) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(Product);
            entityManager.getTransaction().commit();
            return Optional.of(Product);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }
}