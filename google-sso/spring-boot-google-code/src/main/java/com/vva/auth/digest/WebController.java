package com.vva.auth.digest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@RestController
public class WebController {

    @Autowired
    private OAuth2RestTemplate restTemplate;


    @RequestMapping("/user")
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();

        OAuth2Authentication userAuthentication = (OAuth2Authentication) principal;
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) userAuthentication.getDetails();
        Map<String, String> profileDetails = (Map)userAuthentication.getUserAuthentication().getDetails();
        map.put("name", userAuthentication.getName());
        map.put("token", details.getTokenValue());
        map.put("token_type", details.getTokenType());
        map.put("roles", Objects.toString(userAuthentication.getAuthorities()));


        map.put("profile_url", profileDetails.get("picture"));
        map.put("gender", profileDetails.get("gender"));
        map.put("locale", profileDetails.get("locale"));

        return map;
    }

    @RequestMapping("/drive-list")
    public List<String> list() {
        List items = (List)restTemplate.getForEntity("https://www.googleapis.com/drive/v2/files", Map.class).getBody().get("items");
        return (List<String>) items.stream().map(v -> ((Map)v).get("title")).map(Object::toString).collect(Collectors.toList());
    }
}
