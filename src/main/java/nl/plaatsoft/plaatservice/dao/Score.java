package nl.plaatsoft.plaatservice.dao;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The Class Score.
 * 
 * @author wplaat
 */
@Entity
@Table(name = "score")
public class Score {

	/** The sid. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long sid;
    
    /** The product. */
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
        
    /** The user. */
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    	
	/** The dt. */
	private long dt;
	
	/** The score. */
	private long score;
	
	/** The level. */
	private long level;	
	
	/**
	 * Instantiates a new score.
	 */
	public Score() {    	  
	}
	  
	/**
	 * Instantiates a new score.
	 *
	 * @param user the user
	 * @param product the product
	 * @param dt the dt
	 * @param score the score
	 * @param level the level
	 */
	public Score(User user, Product product, long dt, long score, long level) {
		super();
		this.user = user;
		this.product = product;		
		this.dt = dt;
		this.score = score;
		this.level = level;
	}
	
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Score [sid=" + sid + ", user=" + user+ ", product=" + product + ", dt=" + dt + ", score=" + score + ", level="+ level + "]";
	}
	
	/**
	 * Gets the sid.
	 *
	 * @return the sid
	 */
	public long getSid() {
		return sid;
	}
	
	/**
	 * Sets the sid.
	 *
	 * @param id the new sid
	 */
	public void setId(Integer sid) {
		this.sid = sid;
	}
		
	/**
	 * Gets the dt.
	 *
	 * @return the dt
	 */
	public long getDt() {
		return dt;
	}
	
	/**
	 * Gets the product.
	 *
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Sets the product.
	 *
	 * @param product the new product
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Sets the dt.
	 *
	 * @param dt the new dt
	 */
	public void setDt(long dt) {
		this.dt = dt;
	}
	
	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public long getScore() {
		return score;
	}
	
	/**
	 * Sets the score.
	 *
	 * @param score the new score
	 */
	public void setScore(long score) {
		this.score = score;
	}
	
	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public long getLevel() {
		return level;
	}
	
	/**
	 * Sets the level.
	 *
	 * @param level the new level
	 */
	public void setLevel(long level) {
		this.level = level;
	}
}
