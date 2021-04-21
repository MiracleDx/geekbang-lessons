package com.dongx.security.gitee.oauth2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
@Controller
public class OAuth2Controller {
	
	@Resource
	private RestTemplate restTemplate;
	
	@Resource
	private ObjectMapper objectMapper;
	
	private String clientId = "0e3b7f8d492e1619237198889a656e208264e15dc925fc2f5e777760d9901e25";
	
	private String clientSecret = "ecd3fae7701afcff7b1f9e6900d8a85521e3a60a8bedb20b6d6444132ac3810e";
	
	private String redirectUri = "http://gitee-oauth2:8080/oauth.html";
	
	@ResponseBody
	@PostMapping("/redirectToGitee")
	public String redirectToGitee(@RequestBody ScopesDTO scopesDTO) {
		String url = String.format("https://gitee.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code&scope=%s", clientId, redirectUri, String.join( " ", scopesDTO.getScopes()));
		return url;
	}
	
	@ResponseBody
	@PostMapping("/fetchUserInfo")
	public String fetchUserInfo(@RequestBody AuthorizeCodeDTO authorizeCodeDTO) {
		String url = String.format("https://gitee.com/oauth/token?grant_type=authorization_code&code=%s&client_id=%s&redirect_uri=%s&client_secret=%s", authorizeCodeDTO.getCode(), clientId, redirectUri, clientSecret);
		
		MultiValueMap headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		HttpEntity httpEntity = new HttpEntity(null, headers);
		
		String result = restTemplate.postForObject(url, httpEntity, String.class);

		try {
			JsonNode jsonNode = objectMapper.readTree(result);
			String accessToken = objectMapper.readTree(result).get("access_token").asText();
			String userInfoUrl = "https://gitee.com/api/v5/user?access_token={access_token}";
			Map<String, Object> params = new HashMap<String, Object>(1) {{
				put("access_token", accessToken);
			}};
			String userInfo = restTemplate.getForObject(userInfoUrl, String.class, params);
			return userInfo;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@ResponseBody
	@PostMapping("/fetchUserInfoByPass")
	public String fetchUserInfoByPass(@RequestBody AuthorizePassDTO authorizePassDTO) {
		String url = "https://gitee.com/oauth/token";

		MultiValueMap headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", "application/x-www-form-urlencoded");

		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>() {{
			add("username", authorizePassDTO.getEmail());
			add("password", authorizePassDTO.getPassword());
			add("client_id", clientId);
			add("client_secret", clientSecret);
			add("scope", String.join( " ", authorizePassDTO.getScopes()));
			add("grant_type", "password");
		}};
		
		HttpEntity httpEntity = new HttpEntity(params, headers);

		String result = restTemplate.postForObject(url, httpEntity, String.class);

		try {
			JsonNode jsonNode = objectMapper.readTree(result);
			String accessToken = objectMapper.readTree(result).get("access_token").asText();
			String userInfoUrl = "https://gitee.com/api/v5/user?access_token={access_token}";
			Map<String, Object> tokenParams = new HashMap<String, Object>(1) {{
				put("access_token", accessToken);
			}};
			String userInfo = restTemplate.getForObject(userInfoUrl, String.class, tokenParams);
			return userInfo;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
