package nodes;

import java.io.Serializable;
import java.util.UUID;

public class Civilian  implements Individual, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID id;
	private String name;
	private String gender;
	private String location;
	private int age;


	public Civilian(String name, String gender,  String location, int age) {
		this.id = UUID.randomUUID();
		this.id = UUID.randomUUID();
		this.name = name;
		this.gender = gender;
		this.location = location;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}



	public int getAge() {
		return age;
	}

	
    @Override
    public String toString() {
        return location;
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
	public int compareTo(Individual o) {
		// TODO Auto-generated method stub
		return 0;
	}

}