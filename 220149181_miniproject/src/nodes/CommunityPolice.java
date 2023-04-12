package nodes;

import java.io.Serializable;
import java.util.UUID;

public class CommunityPolice implements Individual, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID id;
	private String location;
	private int numberOfMemebers;
	private int availableSpace;

	public CommunityPolice(String location, int numberOfMemebers, int availableSpace) {
		this.id = UUID.randomUUID();
		this.location = location;
		this.numberOfMemebers = numberOfMemebers;
		this.availableSpace = availableSpace;

	}

	public int getNumberOfMemebers() {
		return numberOfMemebers;
	}

	public int getAvailableSpace() {
		return availableSpace;
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
