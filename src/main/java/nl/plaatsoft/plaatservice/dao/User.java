package nl.plaatsoft.plaatservice.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class User.
 * 
 * @author wplaat
 */
@Entity
@Table(name = "user")
public class User {
    
	/** The uid. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uid")
    private long uid;
    
    /** The ip. */
    private String ip; 
    
    /** The username. */
    private String username;       
    
    /** The nickname. */
    private String nickname;
    
    /** The country. */
    private String country;
    
    /** The city. */
    private String city;
       
    /**
     * Instantiates a new user.
     */
    public User() {    	  
    }
    
    /**
     * Instantiates a new user.
     *
     * @param ip the ip
     * @param username the username
     * @param nickname the nickname
     * @param country the country
     * @param city the city
     */
    public User(String ip, String username, String nickname, String country, String city) {
		super();
		this.ip = ip;
		this.username = username;
		this.nickname = nickname;
		this.country = country;
		this.city = city;
	}

    
    /**
     * To string.
     *
     * @return the string
     */
    @Override
	public String toString() {
		return "User [uid=" + uid + ", ip=" + ip + ", username=" + username + ", nickname=" + nickname + ", country=" + country + ", city=" + city + "]";
	}
    
	/**
	 * Gets the uid.
	 *
	 * @return the uid
	 */
	public long getUid() {
		return uid;
	}

	/**
	 * Sets the uid.
	 *
	 * @param uid the new uid
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}

	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the ip.
	 *
	 * @param ip the new ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the nickname.
	 *
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Sets the nickname.
	 *
	 * @param nickname the new nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	
	
}
