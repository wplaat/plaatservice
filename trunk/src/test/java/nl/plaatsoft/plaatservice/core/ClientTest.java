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

import org.junit.Test;

/**
 * The Class ClientTest.
 * 
 * @author wplaat
 */
public class ClientTest {
	   	
	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( ClientTest.class);
	
	/**
	 * version Url Test
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void versionUrlTest() throws ClientProtocolException, IOException {

		  Config config = new Config();
		
		  Server server = new Server();
		  server.start();
		  		 		 
		  String url = "http://" + config.getIp() + ":" + config.getPort() + config.getVersionUri();
		  log.info("Start client {}", url);
		  
		  HttpClient client = HttpClientBuilder.create().build();
		  HttpGet request = new HttpGet(url);
		  request.addHeader("accept", "application/json");	          
		  
		  HttpResponse response = client.execute(request);	     
		  	      
	      assertTrue(response.getStatusLine().getStatusCode() == 200);
	}
}
