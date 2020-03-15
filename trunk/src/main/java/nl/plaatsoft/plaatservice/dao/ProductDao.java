package nl.plaatsoft.plaatservice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class ProductDao.
 * 
 * @author wplaat
 */
public class ProductDao extends BaseDao {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( ProductDao.class);
	
	/**
	 * Creates the.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void create() throws SQLException {
		
		log.debug("create");
		
		String sql = "CREATE TABLE IF NOT EXISTS product" +
			"(" +
		    	"pid bigint NOT NULL, "+
		    	"name text, "+
		    	"version text, "+
		    	"os text, "+
		    	"CONSTRAINT product_pkey PRIMARY KEY (pid) "+
 		    ")";
		
		execute(sql);
	}

	/**
	 * Drop.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void drop() throws SQLException {
		
		log.debug("drop");
		String sql = "DROP TABLE IF EXISTS product";
		execute(sql);
	}
	
	/**
	 * Truncate.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void truncate() throws SQLException {
		
		log.debug("truncate");
			
	    String sql = "TRUNCATE TABLE product";
	    execute(sql);
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
		
		log.debug("insert");
		
		String sql = "INSERT INTO product (pid, name, version, os) VALUES (1, '"+name+"', '"+version+"', '"+os+"')";
		
		execute(sql);
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
		
		log.debug("select");
		
		int pid = 0;
		Statement stmt=null;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT pid FROM product where name='"+name+"' and version='"+version+"' and os='"+os+"'";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){
				pid  = rs.getInt("pid");
			}
		} catch (Exception e) {	
			log.error(e.getMessage());
		} finally {
			if (stmt!=null) {
				stmt.close();
			}
		}	
	    return pid;  
	}
	
	/**
	 * Select.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void select() throws SQLException {

	    log.debug("select");
	    
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
}
