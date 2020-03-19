package nl.plaatsoft.plaatservice.core;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.util.IOUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class ClientTest.
 * 
 * @author wplaat
 */
public class ServerTest {
	   	
	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( ServerTest.class);
	
	/** The url. */
	private String url;
	
	/** The config. */
	private Config config;
	
	/**
	 * Setup.
	 */
	@Before 
	public void setup() {
		 config = new Config();		 
		 url = "http://" + config.getIp() + ":" + config.getPort() + config.getUri();
	}
	
	/**
	 * base Url Test.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void baseUrlTest() throws ClientProtocolException, IOException {
				
		  Server server = new Server();		  
		  server.start();
		  		 		 
		  log.info("Start client {}", url);
		  
		  HttpClient client = HttpClientBuilder.create().build();
		  HttpGet request = new HttpGet(url);
		  request.addHeader("accept", "application/json");	          
		  
		  HttpResponse response = client.execute(request);	     
		  	      
	      assertTrue(response.getStatusLine().getStatusCode() == 200);
	      
	      server.stop();
	}
	
	
	/**
	 * Product url test.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void productUrlTest() throws ClientProtocolException, IOException {
		
		  Server server = new Server();
		  server.init(); 
		  server.start();
		  		 		 
		  String testUrl = url + "?action=getProduct&product=PlaatService&version=1.0.0&os=Windows10";
		  log.info("Start client {}", testUrl);
		  
		  HttpClient client = HttpClientBuilder.create().build();
		  HttpGet request = new HttpGet(testUrl);
		  request.addHeader("accept", "application/json");	          
		  
		  HttpResponse response = client.execute(request);	    
		  	      
	      assertTrue(response.getStatusLine().getStatusCode() == 200);
	      
	      server.stop();
	}
	
	/**
	 * User url test.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void userUrlTest() throws ClientProtocolException, IOException {
		
		  Server server = new Server();
		  server.init(); 
		  server.start();
		  		 		 
		  String testUrl = url + "?action=getUser&ip=127.0.0.1&username=wplaat&nickname=wplaat&country=netherlands&city=gouda";
		  log.info("Start client {}", testUrl);
		  
		  HttpClient client = HttpClientBuilder.create().build();
		  HttpGet request = new HttpGet(testUrl);
		  request.addHeader("accept", "application/json");	          
		  
		  HttpResponse response = client.execute(request);	    
		  
	      assertTrue(response.getStatusLine().getStatusCode() == 200);
	      
	      server.stop();
	}
	
	/**
	 * User update url test.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void userUpdateUrlTest() throws ClientProtocolException, IOException {
		
		  Server server = new Server();
		  server.init(); 
		  server.start();
		  		 		 
		  String testUrl = url + "?action=setUser&ip=127.0.0.1&username=wplaat&nickname=wplaat2";
		  log.info("Start client {}", testUrl);
		  
		  HttpClient client = HttpClientBuilder.create().build();
		  HttpGet request = new HttpGet(testUrl);
		  request.addHeader("accept", "application/json");	          
		  
		  HttpResponse response = client.execute(request);	    
		  
	      assertTrue(response.getStatusLine().getStatusCode() == 200);
	      
	      server.stop();
	}
	
	/**
	 * Score url test.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void scoreUrlTest() throws ClientProtocolException, IOException {
		
		  Server server = new Server();
		  server.init(); 
		  server.start();
		  		 		 		  
		  String testUrl1 = url + "?action=getUser&ip=127.0.0.1&username=wplaat&nickname=wplaat&country=netherlands&city=gouda";
		  log.info("TX: {}", testUrl1);
		  
		  HttpClient client = HttpClientBuilder.create().build();
		  HttpGet request = new HttpGet(testUrl1);
		  request.addHeader("accept", "application/json");	          		  
		  HttpResponse response1 = client.execute(request);	   
		  
		  String response = EntityUtils.toString(response1.getEntity(), "UTF-8");
		  System.out.println("RX: "+response);
		  
		  assertTrue(response1.getStatusLine().getStatusCode() == 200);
		  
		  // -------
		  		  
		  String testUrl2 = url + "?action=getProduct&product=PlaatService&version=1.0.0&os=Windows10";
		  log.info("TX: {}", testUrl2);
		  
		  HttpClient client2 = HttpClientBuilder.create().build();
		  HttpGet request2 = new HttpGet(testUrl2);
		  request2.addHeader("accept", "application/json");	          		  
		  HttpResponse response2 = client2.execute(request2);	   
		  
		  response = EntityUtils.toString(response2.getEntity(), "UTF-8");
		  System.out.println("RX: "+response);
		  	      
	      assertTrue(response2.getStatusLine().getStatusCode() == 200);
	      	      
		  // -------
		  
		  HttpClient client3 = HttpClientBuilder.create().build();
		  String testUrl3 = url + "?action=setScore&uid=1&pid=2&dt=1&score=1&level=1";
		  log.info("TX: {}", testUrl3);
		  
		  HttpGet request3 = new HttpGet(testUrl3);
		  request3.addHeader("accept", "application/json");	          		  
		  HttpResponse response3 = client3.execute(request3);	   
		  
		  response = EntityUtils.toString(response3.getEntity(), "UTF-8");
		  System.out.println("RX: "+response);
		  
	      assertTrue(response3.getStatusLine().getStatusCode() == 200);
	      
	      server.stop();
	}
}
