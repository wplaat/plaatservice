package nl.plaatsoft.plaatservice.core;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class Config.
 * 
 * @author wplaat
 */
public class Config {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( Config.class);	
	
	/** The port. */
	private int port;
	
	/** The uri. */
	private String uri;
	
	/**
	 * Instantiates a new config.
	 */
	public Config() {
		
		try {
			Properties prop = new Properties();
			String propFileName = "plaatservice.properties";
 
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			port = Integer.valueOf(prop.getProperty("port"));
			uri = prop.getProperty("uri");
			
			inputStream.close();

		} catch (Exception e) {
			log.error(e.getMessage());
		} 
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Gets the uri.
	 *
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}
}
