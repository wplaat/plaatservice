package nl.plaatsoft.plaatservice.core;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class Server.
 * 
 * @author wplaat
 */
public class Server {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( Server.class);
		
	private static String json = "{\"Java-RedSquare\":\"0.3\",\"Java-KnightsQuest\":\"0.4\",\"PlaatEnergy\":\"1.6\",\"PlaatScrum\":\"1.3\",\"PlaatProtect\":\"0.6\",\"PlaatSign\":\"1.1\",\"PlaatService\":\"0.2\",\"PlaatDishes\":\"0.1\",\"Windows-ChatCostCalc\":\"0.50\",\"Windows-WarQuest\":\"1.6\",\"Windows-RedSquare\":\"1.0\",\"Windows-PlaatStats\":\"1.1\",\"Windows-PlaatScore\":\"0.70\",\"Android-WarQuest\":\"1.0\",\"Android-RedSquare\":\"0.1\",\"Symbian-WarQuest\":\"1.0\",\"Linux-WarQuest\":\"1.0\",\"Linux-RedSquare\":\"1.0\",\"Wii-BibleQuiz\":\"0.95\",\"Wii-KnightsQuest\":\"0.1\",\"Wii-Pong2\":\"1.0\",\"Wii-RedSquare\":\"1.0\",\"Wii-SpaceBubble\":\"0.98\",\"Wii-TowerDefense\":\"0.98\",\"Drupal-Address\":\"4.2\",\"Drupal-EventNotification\":\"2.2\",\"Drupal-ChurchAdmin\":\"1.1\"}";
		
	/**
	 * Start.
	 */
	public void start() {
		
		Config config = new Config();
		
		HttpServer server = ServerBootstrap.bootstrap()
				.setListenerPort(config.getPort())
				.registerHandler(config.getUri(), new HttpRequestHandler() {
	                public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
	                	
	                	log.info("RX: HTTP request");
	                		              
	                    response.setStatusCode(HttpStatus.SC_OK);
	                    response.setHeader("Server", "PlaatServer 1.0.0");
	                    response.setHeader("Content-type", "application/json");
	                    response.setEntity(new StringEntity(json));
	                    
	                    log.info("TX: HTTP response 0x200");
	                }
	      }).create();
	    try {
			server.start();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
        log.info("Server Starts");
        
        Server server = new Server();
        server.start();
    }
}
