package nl.plaatsoft.plaatservice.core;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class ClientTest.
 * 
 * @author wplaat
 */
public class ClientTest {
	   	
	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( ClientTest.class);
	
	/** The config. */
	private Config config;
	
	/**
	 * Setup.
	 */
	@Before 
	public void setup() {
		 config = new Config();
	}
	
	/**
	 * version Url Test.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void versionUrlTest() throws ClientProtocolException, IOException {
				
		  Server server = new Server();		  
		  server.start();
		  		 		 
		  String url = "http://" + config.getIp() + ":" + config.getPort() + config.getVersionUri();
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
		  		 		 
		  String url = "http://" + config.getIp() + ":" + config.getPort() + config.getProductUri() + "?product=PlaatService&version=1.0.0&os=Windows10";
		  log.info("Start client {}", url);
		  
		  HttpClient client = HttpClientBuilder.create().build();
		  HttpGet request = new HttpGet(url);
		  request.addHeader("accept", "application/json");	          
		  
		  HttpResponse response = client.execute(request);	    
		  	      
	      assertTrue(response.getStatusLine().getStatusCode() == 200);
	      
	      server.stop();
	}
}
