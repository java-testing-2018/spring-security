package com.vva.auth.digest;

import com.google.common.collect.ImmutableMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/")
public class RestApiController {

    @GetMapping("todos")
    public List<Map<String, Object>> getAll() {
        return asList(ImmutableMap.of("Hello", "World"));
    }


    @GetMapping("api")
    public List<Map<String, Object>> getApi() {
        return asList(ImmutableMap.of("Hello", "World"));
    }

    @GetMapping("admin-api")
    public List<Map<String, Object>> getInfoAdmin() {
        return asList(ImmutableMap.of("Hello", "World"));
    }

}
