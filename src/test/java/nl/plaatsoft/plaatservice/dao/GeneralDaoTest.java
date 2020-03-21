package nl.plaatsoft.plaatservice.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;

import nl.plaatsoft.plaatservice.core.Config;

/**
 * The Class GeneralDaoTest.
 * 
 * @author wplaat
 */
public class GeneralDaoTest {
	
	/** The score repository. */	
	protected ScoreDao scoreDao;
	
	/** The user repository. */
	protected UserDao userDao;
	
	/** The product repository. */
	protected ProductDao productDao;
	
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
	    	    
	    scoreDao.truncate();
	    productDao.truncate();
	    userDao.truncate();
	}
}
