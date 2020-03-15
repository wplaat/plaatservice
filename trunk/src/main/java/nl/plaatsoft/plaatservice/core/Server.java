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
	 * Version handler.
	 *
	 * @return the http request handler
	 */
	private HttpRequestHandler versionHandler() {
		
		return new HttpRequestHandler() {
            public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
            	
            	log.info("RX: {}", request);
            	
                response.setStatusCode(HttpStatus.SC_OK);
                response.setHeader("Server", SERVER_NAME);
                response.setHeader("Content-Type", "application/json");
                response.setHeader("Access-Control-Allow-Origin", "*");
                
                Products products = new Products();
                response.setEntity(new StringEntity(products.getItems()));
                	                  	                   
                log.info("TX: {}", response.getStatusLine());
            }};
	}
	
	/**
	 * product handler.
	 *
	 * @return the http request handler
	 */
	private HttpRequestHandler productHandler() {
		
		return new HttpRequestHandler() {
            public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
            	
            	log.info("RX: {}", request);

				String product = context.getAttribute("product").toString();
            	log.info("product={}", product);
            	
                response.setStatusCode(HttpStatus.SC_OK);
                response.setHeader("Server", SERVER_NAME);
                response.setHeader("Content-Type", "application/json");
                response.setHeader("Access-Control-Allow-Origin", "*");
                
                Products products = new Products();
                response.setEntity(new StringEntity(products.getItems()));
                	                  	                   
                log.info("TX: {}", response.getStatusLine());
            }};
	}
	
	/**
	 * Start.
	 */
	public void start() {
		
		Config config = new Config();
				
		log.info("Start server http://{}:{}", config.getIp(), config.getPort());
		try {
			 
			 HttpServer server = ServerBootstrap.bootstrap()
				.setListenerPort(config.getPort())
				.registerHandler(config.getVersionUri(), versionHandler())
				.registerHandler(config.getProductUri(), productHandler())
				.create();
	   
			server.start();
			
		} catch (Exception e) {
			log.info("Error {}", e.getMessage());
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
