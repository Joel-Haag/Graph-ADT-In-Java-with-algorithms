package nodes;

public class Incident {
	private Civilian civilian;
	private SecurityCompany securityCompany;
	private CommunityPolice communityPolice;
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

	public void setDistance(Double distance) {
		this.distance = distance;
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

}
