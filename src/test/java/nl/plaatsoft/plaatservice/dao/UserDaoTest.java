package nl.plaatsoft.plaatservice.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

/**
 * The Class ProductDaoTest.
 * 
 * @author wplaat
 */
public class UserDaoTest extends GeneralDaoTest  {

	/**
	 * Find By Id.
	 */
	@Test
	public void findById() {
		        				
		User user1 = userDao.save(new User("127.0.0.1", "lplaat", "leo", "Netherlands", "Gouda")).get();              
		assertEquals(1, user1.getUid());    
		
		User user2 = userDao.save(new User("127.0.0.1", "bplaat", "leo", "Netherlands", "Gouda")).get();              
		assertEquals(2, user2.getUid());    
		
        User user3 = userDao.findById(1).get();        
        assertEquals(1, user3.getUid());        
	}
	
	/**
	 * Find All.
	 */
	@Test
	public void findAll() {
		        				
		userDao.save(new User("127.0.0.1", "wplaat", "willie", "Netherlands", "Gouda"));
		userDao.save(new User("127.0.0.1", "bplaat", "bassie", "Netherlands", "Gouda"));
		userDao.save(new User("127.0.0.1", "lplaat", "leo", "Netherlands", "Gouda"));
               
        List<User> users = userDao.findAll();
        
        assertEquals(3, users.size());        
	}
		
	/**
	 * Find existing user
	 */
	@Test
	public void findExistingUser() {
		    	        	        
		User user1 = userDao.save(new User("127.0.0.1", "wplaat", "willie", "Netherlands", "Gouda")).get();
		User user2 = userDao.save(new User("127.0.0.1", "bplaat", "bassie", "Netherlands", "Gouda")).get();
	               
		assertEquals(1, user1.getUid());
		assertEquals(2, user2.getUid());  
		
	    Optional<User> user =  userDao.findByName("127.0.0.1", "wplaat", "willie", "Netherlands", "Gouda");
	    
	    assertEquals(user1.getUid(), user.get().getUid());   
	}
	
	
	/**
	 * Find new user
	 */
	@Test
	public void findNewUser() {
		    	        	        
		User user1 = userDao.save(new User("127.0.0.1", "wplaat", "willie", "Netherlands", "Gouda")).get();
		User user2 = userDao.save(new User("127.0.0.1", "bplaat", "bassie", "Netherlands", "Gouda")).get();
	               
		assertEquals(1, user1.getUid());
		assertEquals(2, user2.getUid()); 
	               
	    // New entry is created
		Optional<User> user =  userDao.findByName("127.0.0.1", "wplaat1", "willie", "Netherlands", "Gouda");   
	    
	    assertTrue(user.isPresent()==true);   
	    assertEquals(3, user.get().getUid());   
	}
}
