package nl.plaatsoft.plaatservice.dao;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import nl.plaatsoft.plaatservice.core.Config;

/**
 * The Class ScoreDaoTest.
 * 
 * @author wplaat
 */
public class ScoreDaoTest {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( ScoreDaoTest.class);
	
	/** The score repository. */	
	private ScoreDao scoreDao;
	
	/** The user repository. */
	private UserDao userDao;
	
	/** The product repository. */
	private ProductDao productDao;
	
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
	    scoreDao = new ScoreDao(entityManager);
	    userDao = new UserDao(entityManager);
	    productDao = new ProductDao(entityManager);	    
	}
		
	/**
	 * Find by user score.
	 */
	@Test
	public void findByUserScore() {
		
		User user = userDao.save(new User("127.0.0.1", "wplaat", "willie", "Netherlands", "Gouda")).get();
		
		Product product = productDao.save(new Product("PlaatService", "0.1.0", "Windows10")).get();
				
		scoreDao.save(new Score(user, product, "1", 10, 1));
		scoreDao.save(new Score(user, product, "2", 20, 1));
	               
	    List<Score> scores =  scoreDao.findByUserScore(user, product);
	    assertEquals(2, scores.size());   
	    
	    //scoreDao.truncate();
	    //userDao.truncate();
	    //productDao.truncate();
	}
	
	/**
	 * Find by top score.
	 */
	@Test
	public void findByTopScore() {
		
		User user1 = userDao.save(new User("127.0.0.1", "wplaat", "willie", "Netherlands", "Gouda")).get();
		User user2 = userDao.save(new User("127.0.0.1", "bplaat", "bassie", "Netherlands", "Gouda")).get();
		User user3 = userDao.save(new User("127.0.0.1", "lplaat", "leo", "Netherlands", "Gouda")).get();
		
		Product product1 = productDao.save(new Product("PlaatService", "0.2.0", "Windows10")).get();
		Product product2 = productDao.save(new Product("PlaatService", "0.3.0", "Windows10")).get();
		   		
		scoreDao.save(new Score(user1, product1, "1", 1, 1));
		scoreDao.save(new Score(user1, product1, "2", 2, 1));
		scoreDao.save(new Score(user1, product1, "2", 3, 1));
		scoreDao.save(new Score(user2, product2, "2", 4, 2));
		scoreDao.save(new Score(user2, product2, "2", 5, 3));
		scoreDao.save(new Score(user2, product2, "2", 6, 4));
		scoreDao.save(new Score(user3, product2, "2", 7, 5));
		scoreDao.save(new Score(user3, product2, "2", 8, 6));
		scoreDao.save(new Score(user3, product2, "2", 9, 7));
		scoreDao.save(new Score(user3, product2, "2", 10, 8));
		scoreDao.save(new Score(user3, product2, "2", 11, 9));
		scoreDao.save(new Score(user3, product2, "2", 12, 10));
		scoreDao.save(new Score(user3, product2, "2", 13, 11));
		scoreDao.save(new Score(user3, product2, "2", 14, 12));
		scoreDao.save(new Score(user3, product2, "2", 15, 13));
	               	            	              
	    List<Score> scores =  scoreDao.findByTopScore(product2);
	    
	    Iterator<Score> iter = scores.iterator();
	    while (iter.hasNext()) {
	    	Score score = (Score) iter.next();
	    	log.debug(score);
	    }
	    assertEquals(10, scores.size());   
	    
	    //scoreDao.truncate();
	    //userDao.truncate();
	    //productDao.truncate();
	}
}
