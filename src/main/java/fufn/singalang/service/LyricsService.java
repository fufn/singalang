package fufn.singalang.service;

import fufn.singalang.dto.LyricsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class LyricsService {

    private final WebClient webClient;

    public LyricsResponse getLyrics(String artist, String title) {
        log.info("Getting lyrics from {}-{}", artist, title);
        String response = requestLyrics(artist, title);
        String romanized = extractLyrics(response, "Romanized");
        String original = extractLyrics(response, "Korean Lyrics", "Japanese");
        String english = extractLyrics(response, "English translation");
        return LyricsResponse.builder()
                .romanized(romanized.split("\n"))
                .english(english.split("\n"))
                .original(original.split("\n"))
                .build();
    }

    private String requestLyrics(String artist, String title) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/lyrics")
                        .queryParam("artist", artist)
                        .queryParam("title", title)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(Exception.class, ex -> Mono.empty())
                .block();
    }


    private static String extractLyrics(String lyrics, String... sections) {
        for (String section : sections) {
            String regex = "\\[" + section + ":\\](.*?)\\[";
            Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
            Matcher matcher = pattern.matcher(lyrics);
            if (matcher.find()) {
                return matcher.group(1).trim();
            }
        }

        // If the section is the last one and doesn't end with another section
        String lastSection = sections[sections.length - 1];
        String lastRegex = "\\[" + lastSection + ":\\](.*)";
        Pattern lastPattern = Pattern.compile(lastRegex, Pattern.DOTALL);
        Matcher lastMatcher = lastPattern.matcher(lyrics);
        if (lastMatcher.find()) {
            return lastMatcher.group(1).trim();
        }

        return "";
    }
}
