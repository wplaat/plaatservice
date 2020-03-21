package nl.plaatsoft.plaatservice.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import nl.plaatsoft.plaatservice.dao.Product;
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
	 * Gets the json user.
	 *
	 * @param user the user
	 * @return the json user
	 */
	public static String getJsonUser(User user) {
		
		JSONObject obj = new JSONObject();
		
	   	obj.put("uid", user.getUid());
	   	obj.put("country", user.getCountry());
	   	obj.put("nickname",user.getNickname());
	   	
	   	return obj.toString();
	}
	
	/**
	 * Gets the json product.
	 *
	 * @param product the product
	 * @return the json product
	 */
	public static String getJsonProduct(Product product) {
		
		JSONObject obj = new JSONObject();
		
	   	obj.put("pid", product.getPid());
	   	obj.put("name", product.getName());
	   	obj.put("version", product.getVersion());
	   	obj.put("os",product.getOs());
	   	
	   	return obj.toString();
	}
	
	/**
	 * Gets the json scores.
	 *
	 * @param scores the scores
	 * @return the json scores
	 */
	public static String getJsonScores(List<Score> scores) {
				
		JSONArray array = new JSONArray();
		
		Iterator<Score> iter = scores.iterator();
		while (iter.hasNext()) {
		    			 
		  	Score score = iter.next();
		    	
		  	JSONObject obj2 = new JSONObject();
		   	obj2.put("nickname", score.getUser().getNickname());
		   	obj2.put("country", score.getUser().getCountry());
		   	
		   	JSONObject obj = new JSONObject();
		   	obj.put("sid", score.getSid());
		   	obj.put("dt", score.getDt());
		   	obj.put("level", score.getLevel());
		   	obj.put("score", score.getScore());		   			   
		   	obj.put("user", obj2);
		   	
		   	array.put(obj);
		}		
		return array.toString();
	}
	
	/**
	 * Gets the json scores.
	 *
	 * @param products the products
	 * @return the json scores
	 */
	public static String getJsonProducts(SortedMap<String, String> products) {
		
		JSONArray array = new JSONArray();
		
		for(Map.Entry<String, String> entry : products.entrySet()) {
						
			 JSONObject obj = new JSONObject();
			 obj.put(entry.getKey(), entry.getValue());
			 array.put(obj);
		}
		return array.toString();
	}
}