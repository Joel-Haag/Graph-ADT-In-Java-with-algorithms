package nodes;

import java.io.Serializable;
import java.util.UUID;

/**
 * Community Police class
 */
public class CommunityPolice implements Individual, Serializable {

	/**
	 * serial UID for serialization.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the Universal Unique Identifier for the  Community Police.
	 */
	private UUID id;
	/**
	 * the Community Police location which will be in the format (x:y).
	 */
	private String location;
	/**
	 * the number of members for this specific Community Police 
	 */
	private int numberOfMemebers;
	/**
	 * the number of available members for this community police 
	 */
	private int availableSpace;
	/**
	 * The Community Police constructor which creates a new instance of a community police.
	 * @param location the location of the Community Police .
	 * @param numberOfMemebers the numberOfMemebers of the Community Police .
	 * @param availableSpace the availableSpace of the Community Police.
	 */
	public CommunityPolice(String location, int numberOfMemebers, int availableSpace) {
		this.id = UUID.randomUUID();
		this.location = location;
		this.numberOfMemebers = numberOfMemebers;
		this.availableSpace = availableSpace;

	}

	/**
	 * function to return the Community Police getNumberOfMemebers
	 * @return Community Police getNumberOfMemebers
	 */
	public int getNumberOfMemebers() {
		return numberOfMemebers;
	}

	/**
	 * function to return the Community Police availableSpace
	 * @return Community Police availableSpace
	 */
	public int getAvailableSpace() {
		return availableSpace;
	}

	/**
	 * function to return the Community Police location
	 * @return Community Police location
	 */
	@Override
	public String getLocation() {
		return location;
	}

	/**
	 * function to return the Community Police id
	 * @return Community Police id
	 */
	@Override
	public UUID getId() {
		return id;
	}

	/**
	 * function to return the Community Police instance, displayed as it's location 
	 * @return Community Police location
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
