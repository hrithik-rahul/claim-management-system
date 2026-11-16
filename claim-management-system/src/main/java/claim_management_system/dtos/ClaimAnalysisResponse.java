package claim_management_system.dtos;

import java.util.List;

public class ClaimAnalysisResponse {
	private String claimCategory;

    private String summary;

    private List<String> missingInformation;

    private String recommendation;

	public String getClaimCategory() {
		return claimCategory;
	}

	public void setClaimCategory(String claimCategory) {
		this.claimCategory = claimCategory;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<String> getMissingInformation() {
		return missingInformation;
	}

	public void setMissingInformation(List<String> missingInformation) {
		this.missingInformation = missingInformation;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
}
