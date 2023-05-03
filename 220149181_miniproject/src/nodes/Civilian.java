package nodes;

import java.io.Serializable;
import java.util.UUID;

/**
 * Civilian class
 */
public class Civilian implements Individual, Serializable {

	/**
	 * serial UID for serialization.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the Universal Unique Identifier for the civilian.
	 */
	private UUID id;
	/**
	 * the civilians name, which will be displayed on the graph.
	 */
	private String name;
	/**
	 * the civilians gender.
	 */
	private String gender;
	/**
	 * the civilians location which will be in the format (x:y).
	 */
	private String location;
	/**
	 * the civilians age.
	 */
	private int age;

	/**
	 * The civilian constructor which creates a new instance of a civilian.
	 * 
	 * @param name     the name of the civilian.
	 * @param gender   the gender of the civilian.
	 * @param location the location of the civilian.
	 * @param age      the age of the civilian.
	 */
	public Civilian(String name, String gender, String location, int age) {
		this.id = UUID.randomUUID();
		this.id = UUID.randomUUID();
		this.name = name;
		this.gender = gender;
		this.location = location;
		this.age = age;
	}

	/**
	 * function to return the civilians name
	 * 
	 * @return civilians name
	 */
	public String getName() {
		return name;
	}

	/**
	 * function to return the civilians gender
	 * 
	 * @return civilians gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * function to return the civilians age
	 * 
	 * @return civilians age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * overriding the to string method, so when this class is displayed, it's
	 * displayed using its location which is useful on the incident handling
	 * @return civilian object with location display
	 */
	@Override
	public String toString() {
		return location;
	}
	
	/**
	 * function to return the civilians location
	 * @return civilians location
	 */
	@Override
	public String getLocation() {
		return location;
	}

	/**
	 * function to return the civilians id
	 * @return civilians id
	 */
	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public int compareTo(Individual o) {
		// TODO Auto-generated method stub
		return 0;
	}

}