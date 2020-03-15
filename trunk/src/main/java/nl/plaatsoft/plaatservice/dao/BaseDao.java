package nl.plaatsoft.plaatservice.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class BaseDao.
 */
public class BaseDao {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( BaseDao.class);
	
	/** The conn. */
	protected Connection conn;
	
	/**
	 * Connect.
	 *
	 * @param driver the driver
	 * @param url the url
	 * @param username the username
	 * @param password the password
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public void connect(String driver, String url, String username, String password) throws SQLException, ClassNotFoundException {
		
		Class.forName(driver); 
		
		Properties props = new Properties();
		props.setProperty("user", username);
		props.setProperty("password", password);

		conn = DriverManager.getConnection(url, props);
	}

	/**
	 * Close.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void close() throws SQLException {
		conn.close();
	}
	
	/**
	 * Execute.
	 *
	 * @param sql the sql
	 * @throws SQLException the SQL exception
	 */
	public void execute(String sql) throws SQLException {
		    
		Statement stmt=null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close(); 
		} catch (Exception e) {	
			log.error(e.getMessage());
		} finally {
			if (stmt!=null) {
				stmt.close();
			}
		}		
	}
}
