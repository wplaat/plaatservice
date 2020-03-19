package nl.plaatsoft.plaatservice.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.TreeMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.plaatsoft.plaatservice.dao.Score;
import nl.plaatsoft.plaatservice.dao.User;

/**
 * The Class Utils.
 */
public class Utils {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( Utils.class);
		
	/**
	 * Instantiates a new score.
	 */
	private Utils() {    	  
	}
	
	/**
	 * Gets the parameter.
	 *
	 * @param uri the uri
	 * @param name the name
	 * @return the parameter
	 */
	public static String getParameter(String uri, String name) {
		
		String value = null;
		
		List<NameValuePair> params;
		try {
			params = URLEncodedUtils.parse(new URI(uri), "UTF-8");
			for (NameValuePair param : params) {
				if (param.getName().equals(name))
	        	  return param.getValue();
	        }
		} catch (URISyntaxException e) {
			log.error(e.getMessage());
		}
			
		return value;		
	}
			
	/**
	 * Gets the json.
	 *
	 * @param items the items
	 * @return the json
	 */
	public static String getJson(TreeMap<String, String> items) {
		String json = null;
    	ObjectMapper mapper = new ObjectMapper();

    	try {
    		json = mapper.writeValueAsString(items);
    	} catch (JsonProcessingException e) {
    		log.error(e.getMessage());
    	}   
    	return json;
	}
		
	/**
	 * Gets the json.
	 *
	 * @param items the items
	 * @return the json
	 */
	public static String getJson(List<Score> scores) {
		String json = null;
    	ObjectMapper mapper = new ObjectMapper();

    	try {
    		json = mapper.writeValueAsString(scores);
    	} catch (JsonProcessingException e) {
    		log.error(e.getMessage());
    	}   
    	return json;
	}
	
	/**
	 * Gets the json.
	 *
	 * @param items the items
	 * @return the json
	 */
	public static String getJson(User user) {
		String json = null;
    	ObjectMapper mapper = new ObjectMapper();

    	try {
    		json = mapper.writeValueAsString(user);
    	} catch (JsonProcessingException e) {
    		log.error(e.getMessage());
    	}   
    	return json;
	}
}
