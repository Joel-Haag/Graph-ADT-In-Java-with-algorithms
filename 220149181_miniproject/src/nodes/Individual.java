package nodes;

import java.util.UUID;

/**
 * Individual  interface that all nodes will inherit
 */
public interface Individual extends Comparable<Individual>{
	
	/**
	 * function to return location of node
	 */
	public String getLocation();

	/**
	 * function to return id of node
	 */
	public UUID getId();

}
