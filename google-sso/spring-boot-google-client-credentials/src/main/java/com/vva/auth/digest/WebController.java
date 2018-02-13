package com.vva.auth.digest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RestController
public class WebController {


    @Autowired
    private OAuth2RestTemplate restTemplate;


    @RequestMapping("/drive-list")
    public List<String> list2(Principal principal) {


        ClientCredentialsResourceDetails resource = (ClientCredentialsResourceDetails) restTemplate.getResource();
        resource.setClientId(principal.getName());
//        resource.setClientSecret(principal.getName());


        List items = (List)restTemplate.getForEntity("https://www.googleapis.com/drive/v2/files", Map.class).getBody().get("items");
        return (List<String>) items.stream().map(v -> ((Map)v).get("title")).map(Object::toString).collect(Collectors.toList());
    }
}
