package nl.plaatsoft.plaatservice.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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
	
	/** The conn. */
	private Connection conn;
	
	public void connect(String url, String username, String password) throws SQLException {
		
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
	 * Insert.
	 *
	 * @param name the name
	 * @param version the version
	 * @param os the os
	 * @throws SQLException the SQL exception
	 */
	public void insert(String name, String version, String os) throws SQLException {
		
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String sql = "INSERT INTO product (pid, name, version, os) VALUES (1, '"+name+"', '"+version+"', '"+os+"')";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			
		} finally {
			if (stmt!=null) {
				stmt.close();
			}
		}
	}
	
	/**
	 * Select.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void select() throws SQLException {

	    log.info("select");
	    Statement stmt = conn.createStatement();
	    String sql = "SELECT pid, name, version, os FROM product";
	    
	    ResultSet rs = stmt.executeQuery(sql);

	    while(rs.next()){
	       int pid  = rs.getInt("pid");
	       String name = rs.getString("name");
	       String version = rs.getString("version");
	       String os = rs.getString("os");

	       log.info("{} {} {} {}",pid ,name, version, os);
	    }

	    rs.close();
	    stmt.close();
	}
	
	/**
	 * Truncate.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void truncate() throws SQLException {
		
		log.info("truncate");
	    Statement stmt = conn.createStatement();
	    String sql = "TRUNCATE TABLE product";
	    stmt.executeUpdate(sql);
	    stmt.close();     
	}
	
	/**
	 * Gets the id.
	 *
	 * @param name the name
	 * @param version the version
	 * @param os the os
	 * @return the id
	 * @throws SQLException the SQL exception
	 */
	public int getId(String name, String version, String os) throws SQLException {
		
		int pid = 0;
		log.info("insert");
	    Statement stmt = conn.createStatement();
	    String sql = "SELECT pid FROM product where name='"+name+"' and version='"+version+"' and os='"+os+"'";
	    ResultSet rs = stmt.executeQuery(sql);

	    while(rs.next()){
	       pid  = rs.getInt("pid");
	    }
	    stmt.close();    
	    
	    return pid;  
	}
}
