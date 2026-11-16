
package claim_management_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import claim_management_system.dtos.ChatRequest;
import claim_management_system.dtos.ClaimAnalysisResponse;
import claim_management_system.model.Claim;
import claim_management_system.service.AIService;
import claim_management_system.service.ClaimService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;
    private final ClaimService claimService;

    public AIController(AIService aiService,ClaimService claimService) {
        this.aiService = aiService;
        this.claimService=claimService;
    }


@PostMapping("/chat")

    public String chat(@RequestBody ChatRequest request) {

        return aiService.chat(request.getMessage());

    }

    @PostMapping("/analyze/{claimId}")

    public ResponseEntity<ClaimAnalysisResponse> analyzeClaim(

            @PathVariable Long claimId,

            Authentication authentication) {

        boolean isManager = authentication.getAuthorities()

                .stream()

                .anyMatch(authority ->

                    authority.getAuthority().equals("ROLE_MANAGER"));

        if (!isManager) {

            return ResponseEntity.status(403).build();

        }

        Claim claim = claimService.getClaimById(claimId);

        ClaimAnalysisResponse analysis = aiService.analyzeClaim(claim);

        return ResponseEntity.ok(analysis);

    }
}
