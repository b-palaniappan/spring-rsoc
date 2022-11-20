package io.c12.bala.rsoc.controller;

import io.c12.bala.rsoc.dto.MovieScene;
import io.c12.bala.rsoc.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
@RequiredArgsConstructor
@MessageMapping("tv.v1")
public class TVController {

    private final MovieService movieService;

    @MessageMapping("movie")
    public Flux<MovieScene> playMovie(Flux<Integer> sceneIndex) {
        return sceneIndex
                .map(index -> index - 1)
                .map(this.movieService::getScene)
                .delayElements(Duration.ofSeconds(1));
    }
}
