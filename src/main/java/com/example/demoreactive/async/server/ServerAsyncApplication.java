package com.example.demoreactive.async.server;

import com.example.demoreactive.async.Event;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
@RequestMapping(value = "/async")
public class ServerAsyncApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServerAsyncApplication.class)
            .properties(Collections.singletonMap("server.port", "8080"))
            .run(args);
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Event> getEvents() {
        Flux<Event> eventFlux = Flux.fromStream(Stream.generate(() -> Event.builder().id(System.currentTimeMillis()).when(new Date()).build()));
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(eventFlux, durationFlux)
                .map(Tuple2::getT1);
    }
}
