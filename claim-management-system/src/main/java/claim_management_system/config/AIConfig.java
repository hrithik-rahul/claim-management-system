package claim_management_system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

@Configuration
public class AIConfig {
	   @Value("${openai.api-key}")
	    private String apiKey;

	    @Value("${openai.model}")
	    private String modelName;

	    @Bean
	    public ChatModel chatModel() {

	        return OpenAiChatModel.builder()
	                .apiKey(apiKey)
	                .modelName(modelName)
	                .build();
	    }

@Bean
public ObjectMapper objectMapper() {
    return new ObjectMapper();
}
}
