package nl.plaatsoft.plaatservice.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Product.
 * 
 * @author wplaat
 */
@Entity
@Table(name = "product")
public class Product {
    
	/** The pid. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pid")
    private long pid;
    
	/** The name. */
    private String name;
    
    /** The version. */
    private String version;
    
    /** The os. */
    private String os;
    
    /**
     * Instantiates a new product.
     */
    public Product() {    	  
    }
    	
	/**
	 * Instantiates a new product.
	 *
	 * @param name the name
	 * @param version the version
	 * @param os the os
	 */
	public Product(String name, String version, String os) {
		super();
		this.name = name;
		this.version = version;
		this.os = os;
	}
	
	
    /**
     * To string.
     *
     * @return the string
     */
    @Override
	public String toString() {
		return "Product [pid=" + pid + ", name=" + name + ", version=" + version + ", os=" + os + "]";
	}
    
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the os.
	 *
	 * @return the os
	 */
	public String getOs() {
		return os;
	}

	/**
	 * Sets the os.
	 *
	 * @param os the new os
	 */
	public void setOs(String os) {
		this.os = os;
	}
	
	/**
	 * Gets the pid.
	 *
	 * @return the pid
	 */
	public long getPid() {
		return pid;
	}
}
