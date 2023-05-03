package nodes;

import java.io.Serializable;

/**
 * Incident class
 */
public class Incident implements Serializable, Comparable<Incident> {
	/**
	 * A civilian instance as a variable
	 */
	public Civilian civilian;
	/**
	 * A SecurityCompany instance as a variable
	 */
	public SecurityCompany securityCompany;
	/**
	 * A CommunityPolice instance as a variable
	 */
	public CommunityPolice communityPolice;
	/**
	 * the distance between two different nodes distance
	 */
	private Double distance;
	/**
	 * the severity of the incident
	 */
	private Integer severity;
	/**
	 * a description of the incident
	 */
	private String description;

	/**
	 * a function to set the civilian
	 * 
	 * @param civilian the civilian being set
	 */
	public void setCivilian(Civilian civilian) {
		this.civilian = civilian;
	}

	/**
	 * a function to set the securityCompany
	 * 
	 * @param securityCompany the securityCompany being set
	 */
	public void setSecurityCompany(SecurityCompany securityCompany) {
		this.securityCompany = securityCompany;
	}

	/**
	 * a function to set the communityPolice
	 * 
	 * @param communityPolice the communityPolice being set
	 */
	public void setCommunityPolice(CommunityPolice communityPolice) {
		this.communityPolice = communityPolice;
	}

	/**
	 * a function to set the securityCompanyDistance
	 * 
	 * @param securityCompanyDistance the securityCompanyDistance being set
	 */
	public void setDistance(String civilianDistance, String securityCompanyDistance) {
		this.distance = 2.0;
	}

	/**
	 * a function to set the severity of the crime
	 * 
	 * @param severity the severity being set
	 */
	public void setSeverity(Integer severity) {
		this.severity = severity;
	}

	/**
	 * a function to set the description of the crime
	 * 
	 * @param description the description being set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * a function to return the civilian in the incident
	 * 
	 * @return the civilian in this incident
	 */
	public Civilian getCivilian() {
		return civilian;
	}

	/**
	 * a function to return the securityCompany in the incident
	 * 
	 * @return the securityCompany in this incident
	 */
	public SecurityCompany getSecurityCompany() {
		return securityCompany;
	}

	/**
	 * a function to return the securityCompany in the incident
	 * 
	 * @return the communityPolice in this incident
	 */
	public CommunityPolice getCommunityPolice() {
		return communityPolice;
	}

	/**
	 * a function to return the distance in the incident
	 * 
	 * @return the distance in this incident
	 */
	public Double getDistance() {
		return distance;
	}

	/**
	 * a function to return the severity in the incident
	 * 
	 * @return the severity in this incident
	 */
	public Integer getSeverity() {
		return severity;
	}

	/**
	 * a function to return the description in the incident
	 * 
	 * @return the description in this incident
	 */
	public String getDescription() {
		return description;
	}

	@Override
	public int compareTo(Incident o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
