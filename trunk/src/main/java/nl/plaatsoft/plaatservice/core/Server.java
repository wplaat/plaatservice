package nl.plaatsoft.plaatservice.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
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
	
	/** The product repository. */
	private ProductDao productRepository;
	
	/** The server. */
	private HttpServer server;
	
	/**
	 * Gets the parameter.
	 *
	 * @param uri the uri
	 * @param name the name
	 * @return the parameter
	 */
	private String getParameter(String uri, String name) {
		
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

            	String name = getParameter(uri, "product");
            	String version = getParameter(uri, "version");
            	String os = getParameter(uri, "os");
            	
            	log.debug("name={}", name);
            	log.debug("version={}", version);
            	log.debug("os={}", os);
            	
            	Product product =  productRepository.findByName(name, version, os);
            	
            	String content;
            	if (product!=null) {
            		content = product.getId().toString();
            	} else {
            		content = "null";
            	}
            	
            	response.setEntity(new StringEntity(content));
            	            	
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
			
			server = ServerBootstrap.bootstrap()
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
				
		log.info("Connect to database {}",config.getDatabaseUrl());
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PlaatService");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        productRepository = new ProductDao(entityManager);
                
        return true;
	}
	
	/**
	 * Stop.
	 */
	public void stop() {
		
		log.info("Stop server");
		
		server.stop();
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
