package nl.plaatsoft.plaatservice.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import nl.plaatsoft.plaatservice.core.Config;

/**
 * The Class ProductDaoTest.
 * 
 * @author wplaat
 */
public class UserDaoTest {

	/** The user repository. */
	private UserDao userDao;
	
	/**
	 * Setup.
	 */
	@Before
	public void setup() { 
			
		Config config = new Config();
		
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.driver", config.getDatabaseDriver());
		properties.put("javax.persistence.jdbc.url", config.getDatabaseUrl());
		properties.put("javax.persistence.jdbc.user", config.getDatabaseUsername());
		properties.put("javax.persistence.jdbc.password", config.getDatabasePassword());
				
		properties.put("hibernate.dialect", config.getHibernateDialect());
		properties.put("hibernate.hbm2ddl.auto", config.getHibernateHbm2ddlAuto());
		properties.put("hibernate.show_sql", config.getHibernateShowSql());
						
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PlaatService", properties);
		
	    EntityManager entityManager = entityManagerFactory.createEntityManager();	       
	    userDao = new UserDao(entityManager);
	}
		
	
	/**
	 * Find By Id.
	 */
	@Test
	public void findById() {
		        				
		userDao.save(new User("127.0.0.1", "lplaat", "leo", "Netherlands", "Gouda"));
               
        User user = userDao.findById(1).get();
        
        assertEquals("1", user.getUid().toString());        
	}
	
	/**
	 * Find All.
	 */
	@Test
	public void findAll() {
		        				
		userDao.save(new User("127.0.0.1", "wplaat", "willie", "Netherlands", "Gouda"));
		userDao.save(new User("127.0.0.1", "bplaat", "bassie", "Netherlands", "Gouda"));
		userDao.save(new User("127.0.0.1", "lplaat", "leo", "Netherlands", "Gouda"));
               
        List<User> users = userDao.findAll();
        
        assertEquals(3, users.size());        
	}
		
	/**
	 * Find existing user
	 */
	@Test
	public void findExistingUser() {
		    	        	        
		userDao.save(new User("127.0.0.1", "wplaat", "willie", "Netherlands", "Gouda"));
		userDao.save(new User("127.0.0.1", "bplaat", "bassie", "Netherlands", "Gouda"));
	               
	    Optional<User> user =  userDao.findByName("127.0.0.1", "wplaat", "willie", "Netherlands", "Gouda");
	    assertEquals("willie", user.get().getNickname());   
	}
	
	
	/**
	 * Find new user
	 */
	@Test
	public void findNewUser() {
		    	        	        
		userDao.save(new User("127.0.0.1", "wplaat", "willie", "Netherlands", "Gouda"));
		userDao.save(new User("127.0.0.1", "bplaat", "bassie", "Netherlands", "Gouda"));
	               
	    // New entry is created
		Optional<User> user =  userDao.findByName("127.0.0.1", "wplaat1", "willie", "Netherlands", "Gouda");   
	    
	    assertTrue(user.isPresent()==true);   
	    assertEquals("3", user.get().getUid().toString());   
	}
}
