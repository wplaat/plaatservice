package nl.plaatsoft.plaatservice.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class UserDao.
 * 
 * @author wplaat
 */
public class UserDao {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( UserDao.class);
	
    /** The entity manager. */
    private EntityManager entityManager;
    
    /**
     * Instantiates a new user dao.
     *
     * @param entityManager the entity manager
     */
    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
        
    /**
     * Find by id.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<User> findById(Integer id) {
    	User user = entityManager.find(User.class, id);
        if (user != null) {
        	return Optional.of(user);
        } else {
        	return Optional.empty();
        }
    }
    /**
     * Find all.
     *
     * @return the list
     */
    public List<User> findAll() {
        return entityManager.createQuery("from User").getResultList();
    }
   
    /**
     * Find by name.
     *
     * @param ip the ip
     * @param username the username
     * @return the optional
     */
     public Optional<User> findByName(String ip, String username) {
    	   	   	
    	 try {
    		 User user = entityManager.createQuery("SELECT a FROM User a WHERE a.username=:username AND a.ip=:ip", User.class)
               .setParameter("username", username)
               .setParameter("ip", ip).getSingleResult();
  		   		 
    		return Optional.of(user);
    	 } catch (Exception e) {
     		 return Optional.empty();
   		 }
     }
    
    
    /**
     * Find by name.
     *
     * @param ip the ip
     * @param username the username
     * @param nickname the nickname
     * @param country the country
     * @param city the city
     * @return the list
     */
    public Optional<User> findByName(String ip, String username, String nickname, String country, String city) {
    	
    	 try {    		     	
    		 User user = entityManager.createQuery("SELECT a FROM User a WHERE a.username=:username AND a.ip=:ip", User.class)
                .setParameter("username", username)
                .setParameter("ip", ip)
                .getSingleResult();
   		
    		 return Optional.of(user);
    		 
    	 } catch (Exception e) {
    		 
    		 // Not found, create it!
    		 User user = new User(ip, username, nickname, country, city);
    		 return save(user);
    	 }    	
    }
    
    /**
     * Save.
     *
     * @param user the user
     * @return the optional
     */
    public Optional<User> save(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            return Optional.of(user);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }
    
    /**
     * Truncate.
     */
    public void truncate() {
    	List<User> users = findAll();
    	Iterator<User> iter = users.iterator();
 	    while (iter.hasNext()) {
 	    	User user = iter.next();
 	    	entityManager.remove(user); 
 	    }
     }
}