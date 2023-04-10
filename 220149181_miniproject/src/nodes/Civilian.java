package nodes;

import java.io.Serializable;
import java.util.UUID;

public class Civilian implements Serializable, Comparable<Civilian> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID id;
	private String name;
	private String gender;
	private String homeStreetName;
	private int age;

	public Civilian(String name, String gender, String streetName, int age) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.gender = gender;
		this.homeStreetName = streetName;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public String getStreetName() {
		return homeStreetName;
	}


	public int getAge() {
		return age;
	}

	public UUID getId() {
		return this.id;
	}
	
    @Override
    public String toString() {
        return name;
    }


	@Override
	public int compareTo(Civilian o) {
		// TODO Auto-generated method stub
		return 0;
	}

}