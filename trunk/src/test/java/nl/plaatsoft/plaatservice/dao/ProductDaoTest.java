package nl.plaatsoft.plaatservice.dao;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import nl.plaatsoft.plaatservice.core.Config;
import nl.plaatsoft.plaatservice.dao.ProductDao;

/**
 * The Class ProductDaoTest.
 */
public class ProductDaoTest {

	/** The productDao. */
	ProductDao productDao;
	
	/**
	 * Before.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	@Before
	public void before() throws SQLException, ClassNotFoundException {
		Config config = new Config();
		
		productDao = new ProductDao();
		productDao.connect(config.getDatabaseDriver(), config.getDatabaseUrl(), config.getDatabaseUsername(), config.getDatabasePassword());
		productDao.drop(); 
		productDao.create();
	}
	
	/**
	 * insert / find.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	public void insertFind() throws SQLException {
		
		productDao.insert("PlaatService","0.4.0","Windows10");
		int pid = productDao.getId("PlaatService","0.4.0","Windows10");
		
		assertEquals(1, pid);
		
		productDao.close();
	}
	
	/**
	 * insert / select.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	public void insertSelect() throws SQLException {
		
		productDao.insert("PlaatService","0.4.0","Windows10");
		
		assertEquals(1, productDao.getId("PlaatService","0.4.0","Windows10"));
		
		productDao.select();
		
		productDao.close();
	}	
	
	/**
	 * Truncate select.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	public void truncateSelect() throws SQLException {
		
		productDao.insert("PlaatService","0.4.0","Windows10");
		productDao.truncate();
		
		int pid = productDao.getId("PlaatService","0.4.0","Windows10");
		
		assertEquals(0, pid);	
		
		productDao.close();
	}	
}
