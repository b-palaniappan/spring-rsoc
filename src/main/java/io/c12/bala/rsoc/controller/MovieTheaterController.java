package io.c12.bala.rsoc.controller;

import io.c12.bala.rsoc.dto.MovieScene;
import io.c12.bala.rsoc.dto.TicketRequest;
import io.c12.bala.rsoc.dto.TicketStatus;
import io.c12.bala.rsoc.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.function.Function;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MovieTheaterController {
    private final MovieService movieService;

    /**
     * Fire and Forget.
     */
    @MessageMapping("ticket.cancel")
    public void cancelTicket(Mono<TicketRequest> request){
        // cancel and refund asynchronously
        request
                .doOnNext(t -> t.setStatus(TicketStatus.CANCELLED))
                .doOnNext(t -> log.info("cancelTicket :: {} :: {}", t.getRequestId(), t.getStatus()))
                .subscribe();
    }

    /**
     * Request Response
     */
    @MessageMapping("ticket.purchase")
    public Mono<TicketRequest> purchaseTicket(Mono<TicketRequest> request){
        return request
                .doOnNext(t -> t.setStatus(TicketStatus.ISSUED))
                .doOnNext(t -> log.info("purchaseTicket :: {} :: {}", t.getRequestId(), t.getStatus()));

    }

    /**
     * Request Response Stream
     */
    @MessageMapping("movie.stream")
    public Flux<MovieScene> playMovie(Mono<TicketRequest> request){
        return request
                .map(t -> t.getStatus().equals(TicketStatus.ISSUED) ? movieService.getScenes() : Collections.emptyList())
                .flatMapIterable(Function.identity())
                .cast(MovieScene.class)
                .delayElements(Duration.ofSeconds(1));
    }
}
