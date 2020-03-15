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
	
	/** The Constant SERVER_NAME. */
	private static final String SERVER_NAME = "PlaatService 1.0.0";
			
	/**
	 * Start.
	 */
	public void start() {
		
		Config config = new Config();
				
		log.info("Start server http://{}:{}{}", config.getIp(), config.getPort(), config.getUri());
		
		HttpServer server = ServerBootstrap.bootstrap()
				.setListenerPort(config.getPort())
				.registerHandler(config.getUri(), new HttpRequestHandler() {
	                public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
	                	
	                	log.info("RX: {}", request);
	                	
	                    response.setStatusCode(HttpStatus.SC_OK);
	                    response.setHeader("Server", SERVER_NAME);
	                    response.setHeader("Content-type", "application/json");
	                    
	                    Products products = new Products();
	                    response.setEntity(new StringEntity(products.getItems()));
	                    	                  	                   
	                    log.info("TX: {}", response.getStatusLine());
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
                
        Server server = new Server();
        server.start();
    }
}
