package com.javaproject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaproject.dto.FlagCodesResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.Dictionary;
import java.util.Hashtable;

@SpringBootApplication
public class JavaProjectApplication {

	public static void main(String[] args) {

		try {
			SpringApplication.run(JavaProjectApplication.class, args);

			// Make the API call
			RestTemplate restTemplate = new RestTemplate();
			String apiUrl = "https://flagcdn.com/en/codes.json";
			String jsonResponse = restTemplate.getForObject(apiUrl, String.class);
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(jsonResponse);

			Dictionary<String, String> dictionary = new Hashtable<>();

			jsonNode.fields().forEachRemaining(entry -> {
				String key = entry.getKey();
				String value = entry.getValue().asText();
				dictionary.put(key, value);
			});

			FlagCodesResponse.codes = dictionary;

		}
		catch (Exception e) {
			e.printStackTrace();
		}




	}

}
