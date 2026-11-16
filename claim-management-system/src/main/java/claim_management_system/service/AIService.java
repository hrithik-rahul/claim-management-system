package claim_management_system.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import claim_management_system.dtos.ClaimAnalysisResponse;
import claim_management_system.model.Claim;
import dev.langchain4j.model.chat.ChatModel;



@Service
public class AIService {

    private final ChatModel chatModel;
    private final ObjectMapper objectMapper;
 

    public AIService(ChatModel chatModel, ObjectMapper objectMapper) {
        this.chatModel = chatModel;
        this.objectMapper = objectMapper;
       
    }


    // New AI claim analysis method
    public ClaimAnalysisResponse analyzeClaim(Claim claim) {
    	
        String prompt = """
                You are an insurance claim analysis assistant.

                Analyze the following claim:

                Claim Type: %s
                Description: %s
                Current Status: %s

                Return your analysis strictly as valid JSON with these fields:
                {
                  "claimCategory": "category",
                  "summary": "brief summary",
                  "missingInformation": ["item1", "item2"],
                  "recommendation": "recommendation"
                }

                Do not approve or reject the claim.
                Provide recommendations only.
                If information is insufficient, mention it.
                """.formatted(
                    claim.getClaimType(),
                    claim.getDescription(),
                    claim.getStatus()
                );

        String aiResponse = chatModel.chat(prompt);
        aiResponse = aiResponse
                .replace("```json", "")
                .replace("```", "")
                .trim();

        try {
            return objectMapper.readValue(
                aiResponse,
                ClaimAnalysisResponse.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI claim analysis", e);
        }
    }

    public String chat(String userMessage) {

        String response = chatModel.chat(userMessage);

        return response;
    }
}
