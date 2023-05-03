package nodes;

import java.io.Serializable;
import java.util.UUID;

/**
 * Security Company class
 */
public class SecurityCompany implements Individual, Serializable {
	/**
	 * serial UID for serialization.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the Universal Unique Identifier for the Security Company.
	 */
	private UUID id;
	/**
	 * the Security Company name, which will be displayed on the graph.
	 */
	private String name;
	/**
	 * the Security Company location which will be in the format (x:y).
	 */
	private String location;
	/**
	 * the Security Company price
	 */
	private Double price;
	/**
	 * The Security Company constructor which creates a new instance of a Security Company.
	 * @param name the name of the Security Company .
	 * @param location the location of the Security Company .
	 * @param price the price of the Security Company.
	 */
	public SecurityCompany(String name, String location, double price) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.location = location;
		this.price = price;

	}

	/**
	 * function to return the Security Company name
	 * @return Security Company name
	 */
	public String getName() {
		return name;
	}

	/**
	 * function to return the Security Company price
	 * @returnSecurity Company price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * function to return the Security Company location
	 * @return Security Company location
	 */
	@Override
	public String getLocation() {
		return location;
	}

	/**
	 * function to return the Security Company id
	 * @return Security Company id
	 */
	@Override
	public UUID getId() {
		return id;
	}

	/**
	 * function to return the Security Company instance of the class, which will display the location
	 * @return Security Company location
	 */
	@Override
	public String toString() {
		return location;
	}

	@Override
	public int compareTo(Individual o) {
		// TODO Auto-generated method stub
		return 0;
	}


}
