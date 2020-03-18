package nl.plaatsoft.plaatservice.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class ScoreDao.
 * 
 * @author wplaat
 */
public class ScoreDao {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( ScoreDao.class);
	
    /** The entity manager. */
    private EntityManager entityManager;
    
    /**
     * Instantiates a new Score dao.
     *
     * @param entityManager the entity manager
     */
    public ScoreDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
        
    /**
     * Find all.
     *
     * @return the list
     */
    public List<Score> findAll() {
        return entityManager.createQuery("from Score").getResultList();
    }
   
    /**
     * Find by User and Product
     *
     * @param name the name
     * @param version the version
     * @param os the os
     * @return the list
     */
    public List<Score> findByUserScore(User user, Product product) {
    	
    	 return entityManager.createQuery("SELECT a FROM Score a WHERE a.user=:user AND a.product=:product ORDER BY a.score desc", Score.class)
                .setParameter("user", user)
                .setParameter("product", product)
                .setMaxResults(15)
                .getResultList();
    }
    
    /**
     * Find by User and Product
     *
     * @param name the name
     * @param version the version
     * @param os the os
     * @return the list
     */
    public List<Score> findByTopScore(Product product) {
    	
    	 return entityManager.createQuery("SELECT a FROM Score a WHERE a.product=:product ORDER BY a.score desc", Score.class)
                .setParameter("product", product)
                .setMaxResults(15)
                .getResultList();
    }
    
    /**
     * Save.
     *
     * @param Score the Score
     * @return the optional
     */
    public Optional<Score> save(Score Score) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(Score);
            entityManager.getTransaction().commit();
            return Optional.of(Score);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }
    
    
    /**
     * Truncate.
     */
    public void truncate() {
    	List<Score> scores = findAll();
    	Iterator<Score> iter = scores.iterator();
 	    while (iter.hasNext()) {
 	    	Score score = iter.next();
 	    	entityManager.remove(score); 
 	    }
     }
}