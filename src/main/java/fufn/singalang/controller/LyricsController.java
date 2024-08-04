package fufn.singalang.controller;

import fufn.singalang.dto.LyricsResponse;
import fufn.singalang.service.LyricsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LyricsController {

    private final LyricsService lyricsService;

    @GetMapping
    public LyricsResponse getLyrics(@RequestParam String artist, @RequestParam String title) {
        log.info("Request [GET] to get lyrics for artist {} and title {}", artist, title);
        return lyricsService.getLyrics(artist, title);
    }
}
