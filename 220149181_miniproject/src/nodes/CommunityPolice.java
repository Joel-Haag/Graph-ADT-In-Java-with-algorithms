package nodes;

import java.io.Serializable;
import java.util.UUID;

public class CommunityPolice implements Serializable, Comparable<CommunityPolice> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID id;
	private String location;
	private int numberOfMemebers;
	private int availableSpace;
	

	public CommunityPolice( String location, int numberOfMemebers, int availableSpace) {
		this.id = UUID.randomUUID();
		this.location = location;
		this.numberOfMemebers = numberOfMemebers;
		this.availableSpace = availableSpace;

	}


	public String getLocation() {
		return location;
	}

	public int getNumberOfMemebers() {
		return numberOfMemebers;
	}
	public int getAvailableSpace() {
		return availableSpace;
	}
	
	public UUID getId() {
		return this.id;
	}
	
    @Override
    public String toString() {
        return location;
    }


	@Override
	public int compareTo(CommunityPolice o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
