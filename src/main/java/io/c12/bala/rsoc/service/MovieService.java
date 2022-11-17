package io.c12.bala.rsoc.service;

import io.c12.bala.rsoc.dto.MovieScene;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final List<MovieScene> scenes = List.of(
            new MovieScene(1, "Scene 1"),
            new MovieScene(2, "Scene 2"),
            new MovieScene(3, "Scene 3"),
            new MovieScene(4, "Scene 4"),
            new MovieScene(5, "Scene 5")
    );

    public List<MovieScene> getScenes() {
        return this.scenes;
    }

    public MovieScene getScene(int index) {
        return this.scenes.get(index);
    }
}
