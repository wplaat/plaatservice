package nl.plaatsoft.plaatservice.core;

import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class Products.
 * 
 * @author wplaat
 */
public class Products {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( Products.class);
	
	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public String getItems() {
		
		String json = null;
		
		ObjectMapper mapper = new ObjectMapper();
			
		TreeMap<String, String> items = new TreeMap<String, String>();
		items.put("Java-RedSquare", "0.3.0");
		items.put("Java-KnightsQuest", "0.4");
		items.put("PlaatEnergy", "1.6");
		items.put("PlaatScrum", "1.3");
		items.put("PlaatProtect", "0.6");
		items.put("PlaatSign", "1.1");
		items.put(General.APP_NAME, General.APP_VERSION);
		items.put("PlaatDishes", "0.1");
		items.put("Windows-ChatCostCalc", "0.50");
		items.put("Windows-WarQuest", "1.6");
		items.put("Windows-RedSquare", "1.0");
		items.put("Windows-PlaatStats", "1.1");
		items.put("Windows-PlaatScore", "0.70");
		items.put("Android-WarQuest", "1.0");
		items.put("Android-RedSquare", "0.1");
		items.put("Symbian-WarQuest", "1.0");
		items.put("Linux-WarQuest", "1.0");
		items.put("Linux-RedSquare", "1.0");
		items.put("Wii-BibleQuiz", "0.95");
		items.put("Wii-KnightsQuest", "0.1");
		items.put("Wii-Pong2", "1.0");
		items.put("Wii-RedSquare", "1.0");
		items.put("Wii-SpaceBubble", "0.98");
		items.put("Wii-TowerDefense", "0.98");
		items.put("Drupal-Address", "4.2");
		items.put("Drupal-EventNotification", "2.2");
		items.put("Drupal-ChurchAdmin", "1.1");
				
		try {
			json = mapper.writeValueAsString(items);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
			 
		return json;			
	 }
}
