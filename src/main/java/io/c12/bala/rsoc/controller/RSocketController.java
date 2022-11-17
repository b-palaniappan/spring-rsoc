package io.c12.bala.rsoc.controller;

import io.c12.bala.rsoc.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Controller
public class RSocketController {

    static final String SERVER = "Server";
    static final String RESPONSE = "Response";
    static final String STREAM = "Stream";
    static final String CHANNEL = "Channel";

    @MessageMapping("request-response")
    public Mono<Message> requestResponse(final Message request) {
        log.info("Received request-response request - {}", request);
        return Mono.just(new Message(SERVER, RESPONSE));
    }

    @MessageMapping("fire-and-forget")
    public Mono<Void> fireAndForget(final Message request) {
        log.info("Received fire-and-forget request: {}", request);
        return Mono.empty();
    }

    @MessageMapping("stream")
    Flux<Message> stream(final Message request) {
        log.info("Received stream request: {}", request);

        return Flux
                // create a new indexed Flux emitting one element every second
                .interval(Duration.ofSeconds(1))
                // create a Flux of new Messages using the indexed Flux
                .map(index -> new Message(SERVER, STREAM, index));
    }

    @MessageMapping("channel")
    Flux<Message> channel(final Flux<Duration> settings) {
        log.info("Received channel request...");

        return settings
                .doOnNext(setting -> log.info("Channel frequency setting is {} second(s).", setting.getSeconds()))
                .doOnCancel(() -> log.warn("The client cancelled the channel."))
                .switchMap(setting -> Flux.interval(setting)
                        .map(index -> new Message(SERVER, CHANNEL, index)));
    }

}
