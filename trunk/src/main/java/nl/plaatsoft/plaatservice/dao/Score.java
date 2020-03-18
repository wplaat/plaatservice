package nl.plaatsoft.plaatservice.dao;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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

	/** The id. */
    @Id
    @GeneratedValue
    private Integer id;
    
    /** The product. */
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
        
    /** The user. */
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    	
	/** The dt. */
	private String dt;
	
	/** The score. */
	private Integer score;
	
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
	public Score(User user, Product product, String dt, Integer score, Integer level) {
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
		return "Score [id=" + id + ", user=" + user+ ", product=" + product + ", dt=" + dt + ", score=" + score + ", level="+ level + "]";
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
		
	/**
	 * Gets the dt.
	 *
	 * @return the dt
	 */
	public String getDt() {
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
	public void setDt(String dt) {
		this.dt = dt;
	}
	
	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}
	
	/**
	 * Sets the score.
	 *
	 * @param score the new score
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	
	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}
	
	/**
	 * Sets the level.
	 *
	 * @param level the new level
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	/** The level. */
	private Integer level;	
}
