package nodes;

import java.io.Serializable;

public class Incident implements Serializable, Comparable<Incident> {
	public Civilian civilian;
	public SecurityCompany securityCompany;
	public CommunityPolice communityPolice;
	private Double distance;
	private Integer severity;
	private String description;

	public void setCivilian(Civilian civilian) {
		this.civilian = civilian;
	}

	public void setSecurityCompany(SecurityCompany securityCompany) {
		this.securityCompany = securityCompany;
	}

	public void setCommunityPolice(CommunityPolice communityPolice) {
		this.communityPolice = communityPolice;
	}

	public void setDistance(String civilianDistance, String securityCompanyDistance) {
		this.distance = 2.0;
	}

	public void setSeverity(Integer severity) {
		this.severity = severity;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Civilian getCivilian() {
		return civilian;
	}

	public SecurityCompany getSecurityCompany() {
		return securityCompany;
	}

	public CommunityPolice getCommunityPolice() {
		return communityPolice;
	}

	public Double getDistance() {
		return distance;
	}

	public Integer getSeverity() {
		return severity;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public int compareTo(Incident o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
