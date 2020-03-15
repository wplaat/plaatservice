package nl.plaatsoft.plaatservice.core;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.junit.Test;

public class ClientTest {
	   	
	@Test
	public void TestClient() throws ClientProtocolException, IOException {

		  DefaultHttpClient httpClient = new DefaultHttpClient();
		
	      HttpGet getRequest = new HttpGet("https://service.plaatsoft.nl/");	         
          getRequest.addHeader("accept", "application/xml");	          
	      HttpResponse response = httpClient.execute(getRequest);	      
	      
	      assertTrue(response.getStatusLine().getStatusCode() == 200);
	}
}
