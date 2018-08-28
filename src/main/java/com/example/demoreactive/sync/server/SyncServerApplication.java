package com.example.demoreactive.sync.server;

import com.example.demoreactive.async.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/sync")
public class SyncServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SyncServerApplication.class)
                .properties(Collections.singletonMap("server.port", "8081"))
                .run(args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }

    @Autowired
    public RestTemplate template;

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEvents() {
        Event[] events = template.getForObject("http://localhost:8080/async/events", Event[].class);
        return ResponseEntity.ok(Arrays.asList(events));
    }
}
