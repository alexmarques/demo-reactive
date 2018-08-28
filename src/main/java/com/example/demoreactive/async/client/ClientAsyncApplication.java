package com.example.demoreactive.async.client;

import com.example.demoreactive.async.Event;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@SpringBootApplication
public class ClientAsyncApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ClientAsyncApplication.class)
                .properties(Collections.singletonMap("server.port", "8081"))
                .run(args);
    }

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    CommandLineRunner runner(WebClient client) {
        return args -> {
            client
                .get()
                .uri("/async/events")
                .retrieve()
                .bodyToFlux(Event.class)
                .subscribe(System.out::println);
        };
    }
}