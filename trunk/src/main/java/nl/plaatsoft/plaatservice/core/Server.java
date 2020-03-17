package nl.plaatsoft.plaatservice.core;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

import nl.plaatsoft.plaatservice.dao.Product;
import nl.plaatsoft.plaatservice.dao.ProductDao;

/**
 * The Class Server.
 * 
 * @author wplaat
 */
public class Server {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( Server.class);
		
	/** The config. */
	private Config config = new Config();
	
	/**
	 * product handler.
	 *
	 * @return the http request handler
	 */
	private HttpRequestHandler productHandler() {
		
		return new HttpRequestHandler() {
            public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
            	
            	log.info("RX: {}", request);
            	
            	String uri = request.getRequestLine().getUri();
            	String method = request.getRequestLine().getMethod();
            	String protocol = request.getRequestLine().getProtocolVersion().toString();
            	log.info("URI={}", uri);
            	log.info("METHOD={}", method);
            	log.info("PROTOCOL={}", protocol);
            	
            	
            	
                           
            	
            	/*if (request instanceof HttpEntityEnclosingRequest) {
                    final HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
                                  
                    log.info(entity.getContentLength());
            	}*/
            	            			            		
            	//log.info("product=",URLDecoder.decode(request.getRequestLine().toString(), "UTF-8"));
            	
				/*String product = (String) request.getParams().getParameter("product");
            	log.info("product={}", product);
            	
            	String version = (String) request.getParams().getParameter("version");
            	log.info("version={}", version);*/
            	
                response.setStatusCode(HttpStatus.SC_OK);
                response.setHeader("Server", General.APP_NAME+" "+General.APP_VERSION);
                response.setHeader("Content-Type", "application/json");
                response.setHeader("Access-Control-Allow-Origin", "*");
                
                	                  	                   
                log.info("TX: {}", response.getStatusLine());
            }};
	}
	
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
                response.setHeader("Server", General.APP_NAME+" "+General.APP_VERSION);
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
		
		try {				 
			log.info("Start server http://{}:{}", config.getIp(), config.getPort());
			
			HttpServer server = ServerBootstrap.bootstrap()
				.setListenerPort(config.getPort())
				.registerHandler(config.getVersionUri(), versionHandler())
				.registerHandler(config.getProductUri(), productHandler())
				.create();
	   
			server.start();
			
		} catch (Exception e) {
			log.error("Error {}", e.getMessage());
		}
	}
	
	
	/**
	 * Inits the.
	 *
	 * @return true, if successful
	 */
	public boolean init() {		
		
		boolean result = true;
		
		log.info("Connect to database {}",config.getDatabaseUrl());
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PlaatService");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        ProductDao productRepository = new ProductDao(entityManager);
        
        List <Product> products =  productRepository.findByName("PlaatService", "0.5.0", "Windows10");
        
        return true;
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
        Server server = new Server();
        if (server.init()) {
        	server.start();
        }
    }
}
