package nodes;

import java.io.Serializable;
import java.util.UUID;

public class SecurityCompany implements Serializable, Comparable<SecurityCompany> {
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

	public String getLocation() {
		return location;
	}

	public Double getPrice() {
		return price;
	}

	public UUID getId() {
		return this.id;
	}
	
    @Override
    public String toString() {
        return name;
    }

	@Override
	public int compareTo(SecurityCompany o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
