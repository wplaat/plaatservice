package nl.plaatsoft.plaatservice.core;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.junit.Before;
import org.junit.Test;

/**
 * The Class ClientTest.
 * 
 * @author wplaat
 */
public class ServerTest {
	   		
	/** The url. */
	private String url;
	
	/** The config. */
	private Config config;
	
	/** The server. */
	private Server server;
	
	/**
	 * Setup.
	 */
	@Before 
	public void setup() {
		 config = new Config();		 
		 url = "http://" + config.getIp() + ":" + config.getPort() + config.getUri();
		 
		 server = new Server();	
		 server.init();
		 server.start();
	}
	
	/**
	 * Sleep.
	 *
	 * @param parameter the parameter
	 */
	private void sleep(long parameter) {	
		try {
			Thread.sleep(parameter);
		} catch (InterruptedException e) {
											
	    }      					   
	}
	
	/**
	 * After.
	 */
	private void after() {
	
		server.stop();
		
		// Workarround else unittest fail in Jenkins.
	    sleep(1000);
	}
	
	/**
	 * base Url Test.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void baseUrlTest() throws ClientProtocolException, IOException {
				
		  CloseableHttpClient client = HttpClientBuilder.create().build();
		  HttpGet request = new HttpGet(url);
		  request.addHeader("accept", "application/json");	          
		  
		  HttpResponse response = client.execute(request);	     
		  	      
	      assertTrue(response.getStatusLine().getStatusCode() == 200);
	     
	      client.close();
	      after();     
	}
	
	
	/**
	 * Product url test.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void productUrlTest() throws ClientProtocolException, IOException {
		  		 		 
		  String testUrl = url + "?action=getProduct&product=PlaatService&version=1.0.0&os=Windows10";
		  
		  CloseableHttpClient client = HttpClientBuilder.create().build();
		  HttpGet request = new HttpGet(testUrl);
		  request.addHeader("accept", "application/json");	          
		  
		  HttpResponse response = client.execute(request);	    
		  	      
	      assertTrue(response.getStatusLine().getStatusCode() == 200);
	      
	      client.close();
	      after();
	}
	
	/**
	 * User url test.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void userUrlTest() throws ClientProtocolException, IOException {
				  		 		 
		  String testUrl = url + "?action=getUser&ip=127.0.0.1&username=wplaat&nickname=wplaat&country=netherlands&city=gouda";
		  
		  CloseableHttpClient client = HttpClientBuilder.create().build();
		  HttpGet request = new HttpGet(testUrl);
		  request.addHeader("accept", "application/json");	          
		  
		  HttpResponse response = client.execute(request);	    
		  
	      assertTrue(response.getStatusLine().getStatusCode() == 200);
	      
	      client.close();
	      after();
	}
	
	/**
	 * User update url test.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void userUpdateUrlTest() throws ClientProtocolException, IOException {
				  		 		 
		  String testUrl = url + "?action=setUser&ip=127.0.0.1&username=wplaat&nickname=wplaat2";
		  
		  CloseableHttpClient client = HttpClientBuilder.create().build();
		  HttpGet request = new HttpGet(testUrl);
		  request.addHeader("accept", "application/json");	          
		  
		  HttpResponse response = client.execute(request);	    
		  
	      assertTrue(response.getStatusLine().getStatusCode() == 200);
	      
	      client.close();
	      after();
	}
	
	/**
	 * user Score url test.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void userScoreUrlTest() throws ClientProtocolException, IOException {
		  		 		 		  
		  String testUrl1 = url + "?action=getUser&ip=127.0.0.1&username=wplaat&nickname=wplaat&country=netherlands&city=gouda";
		  
		  CloseableHttpClient client1 = HttpClientBuilder.create().build();
		  HttpGet request = new HttpGet(testUrl1);
		  request.addHeader("accept", "application/json");	          		  
		  HttpResponse response1 = client1.execute(request);	   
		  
		  assertTrue(response1.getStatusLine().getStatusCode() == 200);
		  
		  client1.close();
		  
		  // -------
		  		  
		  String testUrl2 = url + "?action=getProduct&product=PlaatService&version=1.0.0&os=Windows10";
		  
		  CloseableHttpClient client2 = HttpClientBuilder.create().build();
		  HttpGet request2 = new HttpGet(testUrl2);
		  request2.addHeader("accept", "application/json");	          		  
		  HttpResponse response2 = client2.execute(request2);	   
		  	      
	      assertTrue(response2.getStatusLine().getStatusCode() == 200);
	      
	      client2.close();
		  
	      // -------
		  
	      CloseableHttpClient client3a = HttpClientBuilder.create().build();
		  String testUrl3a = url + "?action=setScore&uid=1&pid=1&dt=1&score=1&level=1";
		  
		  HttpGet request3a = new HttpGet(testUrl3a);
		  request3a.addHeader("accept", "application/json");	          		  
		  HttpResponse response3a = client3a.execute(request3a);	   
	 
	      assertTrue(response3a.getStatusLine().getStatusCode() == 200);
	      
	      client3a.close();
	      
	      // -------
		  
	      CloseableHttpClient client3b = HttpClientBuilder.create().build();
		  String testUrl3b = url + "?action=setScore&uid=1&pid=1&dt=1&score=2&level=1";
		  
		  HttpGet request3b = new HttpGet(testUrl3b);
		  request3b.addHeader("accept", "application/json");	          		  
		  HttpResponse response3b = client3b.execute(request3b);	   
	 
	      assertTrue(response3b.getStatusLine().getStatusCode() == 200);
	      
	      client3b.close();
	      
	      // -------
		  
	      CloseableHttpClient client3c = HttpClientBuilder.create().build();
		  String testUrl3c = url + "?action=setScore&uid=1&pid=1&dt=1&score=3&level=1";
		  
		  HttpGet request3c = new HttpGet(testUrl3c);
		  request3c.addHeader("accept", "application/json");	          		  
		  HttpResponse response3c = client3c.execute(request3c);	   
	 
	      assertTrue(response3c.getStatusLine().getStatusCode() == 200);
	      	      
	      client3c.close();
	      
	      // -------
		  
	      CloseableHttpClient client4 = HttpClientBuilder.create().build();
		  String testUrl4 = url + "?action=getLocalScore&uid=1&pid=1";
		  
		  HttpGet request4 = new HttpGet(testUrl4);
		  request4.addHeader("accept", "application/json");	          		  
		  HttpResponse response4 = client4.execute(request4);	   
		  		  
	      assertTrue(response4.getStatusLine().getStatusCode() == 200);
	      
	      client4.close();
	      
	      after();
	}
		
	/**
	 * top Score url test.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void topScoreUrlTest() throws ClientProtocolException, IOException {
		  		 		 		  
		  String testUrl1 = url + "?action=getUser&ip=127.0.0.1&username=wplaat&nickname=wplaat&country=netherlands&city=gouda";
		  
		  CloseableHttpClient client1 = HttpClientBuilder.create().build();
		  HttpGet request = new HttpGet(testUrl1);
		  request.addHeader("accept", "application/json");	          		  
		  HttpResponse response1 = client1.execute(request);	   
		  
		  assertTrue(response1.getStatusLine().getStatusCode() == 200);
		  
		  client1.close();
		  
		  // -------
		  		  
		  String testUrl2 = url + "?action=getProduct&product=PlaatService&version=1.0.0&os=Windows10";
		  
		  CloseableHttpClient client2 = HttpClientBuilder.create().build();
		  HttpGet request2 = new HttpGet(testUrl2);
		  request2.addHeader("accept", "application/json");	          		  
		  HttpResponse response2 = client2.execute(request2);	   
		  	      
	      assertTrue(response2.getStatusLine().getStatusCode() == 200);
	      	     
	      client2.close();
	      
		  // -------
		  
	      CloseableHttpClient client3a = HttpClientBuilder.create().build();
		  String testUrl3a = url + "?action=setScore&uid=1&pid=1&dt=1&score=1&level=1";
		  
		  HttpGet request3a = new HttpGet(testUrl3a);
		  request3a.addHeader("accept", "application/json");	          		  
		  HttpResponse response3a = client3a.execute(request3a);	   
	 
	      assertTrue(response3a.getStatusLine().getStatusCode() == 200);
	      
	      client3a.close();
	      
	      // -------
		  
	      CloseableHttpClient client3b = HttpClientBuilder.create().build();
		  String testUrl3b = url + "?action=setScore&uid=1&pid=1&dt=1&score=2&level=1";
		  
		  HttpGet request3b = new HttpGet(testUrl3b);
		  request3b.addHeader("accept", "application/json");	          		  
		  HttpResponse response3b = client3b.execute(request3b);	   
	 
	      assertTrue(response3b.getStatusLine().getStatusCode() == 200);
	      
	      client3b.close();
	      
	      // -------
		  
	      CloseableHttpClient client3c = HttpClientBuilder.create().build();
		  String testUrl3c = url + "?action=setScore&uid=1&pid=1&dt=1&score=3&level=1";
		  
		  HttpGet request3c = new HttpGet(testUrl3c);
		  request3c.addHeader("accept", "application/json");	          		  
		  HttpResponse response3c = client3c.execute(request3c);	   
	 
	      assertTrue(response3c.getStatusLine().getStatusCode() == 200);
	      	      
	      client3c.close();
	      
	      // -------
		  
	      CloseableHttpClient client4 = HttpClientBuilder.create().build();
		  String testUrl4 = url + "?action=getGlobalScore&pid=1";
		  
		  HttpGet request4 = new HttpGet(testUrl4);
		  request4.addHeader("accept", "application/json");	          		  
		  HttpResponse response4 = client4.execute(request4);	   
		  		  
	      assertTrue(response4.getStatusLine().getStatusCode() == 200);
	      
	      client4.close();
	      
	      after();
	}
}
