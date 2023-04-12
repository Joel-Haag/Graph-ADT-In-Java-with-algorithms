package nodes;

import java.io.Serializable;
import java.util.UUID;

public class SecurityCompany implements Individual, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID id;
	private String name;
	private String location;
	private Double price;

	public SecurityCompany(String name, String location, double price) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.location = location;
		this.price = price;

	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public UUID getId() {
		return id;
	}

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
