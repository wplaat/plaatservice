package nl.plaatsoft.plaatservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Product.
 */
@Entity
@Table(name = "product")
public class Product {
    
    /** The id. */
    @Id
    @GeneratedValue
    private Integer id;
    
    /** The name. */
    private String name;
    
    /** The version. */
    private String version;
    
    public Product() {    	  
    }
    	
	public Product(String name, String version, String os) {
		super();
		this.name = name;
		this.version = version;
		this.os = os;
	}
	
	/** The os. */
    private String os;

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
}
