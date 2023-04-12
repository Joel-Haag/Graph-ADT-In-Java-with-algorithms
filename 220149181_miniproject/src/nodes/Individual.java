package nodes;

import java.util.UUID;

public interface Individual extends Comparable<Individual>{

	public String getLocation();

	public UUID getId();

}
