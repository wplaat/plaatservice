package nl.plaatsoft.plaatservice.dao;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class ProductDao.
 * 
 * @author wplaat
 */
public class ProductDao {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( ProductDao.class);
	
    /** The entity manager. */
    private EntityManager entityManager;
    
    /**
     * Instantiates a new product dao.
     *
     * @param entityManager the entity manager
     */
    public ProductDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
        
    /**
     * Find all.
     *
     * @return the list
     */
    public List<Product> findAll() {
        return entityManager.createQuery("from Product").getResultList();
    }
   
    /**
     * Find by name.
     *
     * @param name the name
     * @param version the version
     * @param os the os
     * @return the list
     */
    public List<Product> findByName(String name, String version, String os) {
    	
    	return entityManager.createQuery("SELECT a FROM Product a WHERE a.name=:name AND a.version=:version AND a.os=:os", Product.class)
                .setParameter("name", name)
                .setParameter("version", version)
                .setParameter("os", os)
                .getResultList();
    }
    
    /**
     * Save.
     *
     * @param Product the product
     * @return the optional
     */
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