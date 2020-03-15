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

	ProductDao database;
	
	@Before
	public void before() throws SQLException, ClassNotFoundException {
		Config config = new Config();
		
		database = new ProductDao();
		database.connect(config.getDatabaseDriver(), config.getDatabaseUrl(), config.getDatabaseUsername(), config.getDatabasePassword());
		database.drop(); 
		database.create();
	}
	
	/**
	 * insert / find.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	public void insertFind() throws SQLException {
		
		database.insert("PlaatService","0.4.0","Windows10");
		int pid = database.getId("PlaatService","0.4.0","Windows10");
		
		assertEquals(1, pid);
	}
	
	/**
	 * insert / find.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	public void insertSelect() throws SQLException {
		
		database.insert("PlaatService","0.4.0","Windows10");
		database.select();
		
		assertEquals(1, database.getId("PlaatService","0.4.0","Windows10"));
	}	
}
