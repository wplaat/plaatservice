package nl.plaatsoft.plaatservice.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

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
import nl.plaatsoft.plaatservice.dao.Score;
import nl.plaatsoft.plaatservice.dao.ScoreDao;
import nl.plaatsoft.plaatservice.dao.User;
import nl.plaatsoft.plaatservice.dao.UserDao;

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
	
	/** The product dao. */
	private ProductDao productDao;
	
	/** The user dao. */
	private UserDao userDao;
	
	/** The score dao. */
	private ScoreDao scoreDao;
	
	/** The server. */
	private HttpServer httpServer;
		
	/**
	 * Gets the local score.
	 *
	 * @param uri the uri
	 * @return the local score
	 */
	private String getLocalScore(String uri) {
		
		Integer uid = Integer.valueOf(Utils.getParameter(uri, "uid"));
		Integer pid = Integer.valueOf(Utils.getParameter(uri, "pid"));
						
		if ((pid>0) && (uid>0)) {
			
			User user = userDao.findById(uid).get();
			Product product = productDao.findById(pid).get();
			
			List <Score> scores = scoreDao.findByUserScore(user, product);
			return Utils.getJson(scores); 
		} else {
			return "{}";
		}
	}
	
	/**
	 * Gets the global score.
	 *
	 * @param uri the uri
	 * @return the global score
	 */
	private String getGlobalScore(String uri) {
		
		Integer pid = Integer.valueOf(Utils.getParameter(uri, "pid"));
		
		if (pid>0) {
			
			Product product = productDao.findById(pid).get();
			
			List <Score> scores = scoreDao.findByTopScore(product);		     
			return Utils.getJson(scores);
		} else {
			return "{}";
		}
	}
	
	/**
	 * Sets the score.
	 *
	 * @param uri the uri
	 * @return the string
	 */
	private String setScore(String uri) {
				
		Integer uid = Integer.valueOf(Utils.getParameter(uri, "uid"));
		Integer pid = Integer.valueOf(Utils.getParameter(uri, "pid"));
		
		User user = userDao.findById(uid).get();
		Product product = productDao.findById(pid).get();		
		
		Score score = new Score();
		score.setUser(user);
		score.setProduct(product);
		score.setDt(Utils.getParameter(uri, "dt"));
		score.setLevel(Integer.valueOf(Utils.getParameter(uri, "level")));		
		score.setScore(Integer.valueOf(Utils.getParameter(uri, "score")));
						
		if ((score.getDt()!=null) && (user!=null) && (product!=null)) {			
			scoreDao.save(score);
		}
				
		return "{}";
	}
	
	/**
	 * Sets the user.
	 *
	 * @param uri the uri
	 * @return the string
	 */
	private String setUser(String uri) {
		
		String ip = Utils.getParameter(uri, "ip");
    	String username = Utils.getParameter(uri, "username");
    	String nickname = Utils.getParameter(uri, "nickname");
    	
    	if ((ip!=null) && (username!=null) && (nickname!=null)) {
    		Optional<User> user = userDao.findByName(ip, username);
    	
    		if (user.isPresent()) {
    			user.get().setNickname(nickname);
    			userDao.save(user.get());
    			return Utils.getJson(user.get());        		
        	} 
    	}
    	return "{}";
	}

	/**
	 * Gets the user.
	 *
	 * @param uri the uri
	 * @return the user
	 */
	private String getUser(String uri) {
		
		String ip = Utils.getParameter(uri, "ip");
    	String username = Utils.getParameter(uri, "username");
    	String nickname = Utils.getParameter(uri, "nickname");
    	String country = Utils.getParameter(uri, "country");
    	String city = Utils.getParameter(uri, "city");
    	
    	if ((ip!=null) && (username!=null) && (nickname!=null) && (country!=null) && (city!=null)) {
    		Optional<User> user = userDao.findByName(ip, username, nickname, country, city);
    		if (user.isPresent()) {
    			return Utils.getJson(user.get());
    		} 
    	} 
    	return "{}";
	}
		
	
	private String getVersion(String uri) {
		
    	String name = Utils.getParameter(uri, "product");
    	
    	if ((name!=null)) {
    		Optional<Product> product = productDao.findByName(name);
    	
    		TreeMap<String, String> items = new TreeMap<String, String>();
    		if (product.isPresent()) {
    			items.put("version", product.get().getVersion());    			
    		}
    		return Utils.getJson(items);
    	}
    	return "{}";
	}
	
	/**
	 * Gets the product.
	 *
	 * @param uri the uri
	 * @return the product
	 */
	private String getProduct(String uri) {
		
		String name = Utils.getParameter(uri, "product");
    	String version = Utils.getParameter(uri, "version");
    	String os = Utils.getParameter(uri, "os");
    	
    	if ((name!=null) && (version!=null) && (os!=null)) {
    		Optional<Product> product = productDao.findByName(name, version, os);
    	                	         	   		
    		TreeMap<String, String> items = new TreeMap<String, String>();
    		if (product.isPresent()) {    	
    			items.put("pid", product.get().getPid().toString());
    		}
    		return Utils.getJson(items);       		
    	} else {
    		return "{}";
    	}	   
	}
		
	/**
	 * Gets products.
	 *
	 * @return the products.
	 */
	private String getProducts() {
					
		TreeMap<String, String> items = new TreeMap<String, String>();
		
		items.put("PlaatEnergy", "1.6");
		items.put("PlaatScrum", "1.3");
		items.put("PlaatProtect", "0.6");
		items.put("PlaatSign", "1.1");		
		items.put("PlaatDishes", "0.1");
		items.put(General.APP_NAME, General.APP_VERSION);
		
		items.put("Java-RedSquare", "0.4.0");
		items.put("Java-KnightsQuest", "0.4");
		
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
				
		return Utils.getJson(items);  		
	 }
	
	/**
	 * httpHandler.
	 *
	 * @return the http request handler
	 */
	private HttpRequestHandler httpHandler() {
		
		return new HttpRequestHandler() {
            public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
            	
            	String uri = request.getRequestLine().getUri();
            	
            	log.info("RX: {}", request);
            	
            	String action = Utils.getParameter(uri, "action");

                response.setStatusCode(HttpStatus.SC_OK);
                response.setHeader("Server", General.APP_NAME+" "+General.APP_VERSION);
                response.setHeader("Content-Type", "application/json");
                response.setHeader("Access-Control-Allow-Origin", "*");
                                
                if (action==null) {
                	
                	String json = getProducts();
                	response.setEntity(new StringEntity(json));
                	
                } else if (action.equals("getVersion")) {
                	
                	String json = getVersion(uri);
                	response.setEntity(new StringEntity(json));    
                	
                } else if (action.equals("getProduct")) {
                	
                	String json = getProduct(uri);
                	response.setEntity(new StringEntity(json));         		
                	 
                } else if (action.equals("getUser")) {
                
                	String json = getUser(uri);
                	response.setEntity(new StringEntity(json));
                	
                } else if (action.equals("setUser")) {
                    
                	String json = setUser(uri);
                	response.setEntity(new StringEntity(json));
                	
                } else if (action.equals("setScore")) {
                    
                	String json = setScore(uri);
                	response.setEntity(new StringEntity(json));
                	
                } else if (action.equals("getLocalScore")) {
                
                	String json = getLocalScore(uri);
                	response.setEntity(new StringEntity(json));
                	
                } else if (action.equals("getGlobalScore")) {
                
                	String json = getGlobalScore(uri);
                	response.setEntity(new StringEntity(json));   
                }
                	                  	                   
                log.info("TX: {}", response);
            }};
	}
		
	/**
	 * Start.
	 */
	public void start() {
		
		try {				 
			log.info("Init server http://{}:{}", config.getIp(), config.getPort());
			
			httpServer = ServerBootstrap.bootstrap()
				.setListenerPort(config.getPort())
				.registerHandler(config.getUri(), httpHandler())
				.create();
	   
			httpServer.start();
			
		} catch (Exception e) {
			log.error("Error {}", e.getMessage());
		}
	}
	
	
	/**
	 * Init
	 */
	public void init() {		
		
		log.info("Init database {}", config.getDatabaseUrl());
		
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.driver", config.getDatabaseDriver());
		properties.put("javax.persistence.jdbc.url", config.getDatabaseUrl());
		properties.put("javax.persistence.jdbc.user", config.getDatabaseUsername());
		properties.put("javax.persistence.jdbc.password", config.getDatabasePassword());
				
		properties.put("hibernate.dialect", config.getHibernateDialect());
		properties.put("hibernate.hbm2ddl.auto", config.getHibernateHbm2ddlAuto());
		properties.put("hibernate.show_sql", config.getHibernateShowSql());
						
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PlaatService", properties);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        productDao = new ProductDao(entityManager);
        userDao = new UserDao(entityManager);
        scoreDao = new ScoreDao(entityManager);
	}
	
	/**
	 * Stop.
	 */
	public void stop() {
		
		log.info("Stop server");
		
		httpServer.stop();
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
        Server server = new Server();
        server.init();
        server.start();      
    }
}
