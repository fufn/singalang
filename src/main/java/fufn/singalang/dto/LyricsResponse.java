package fufn.singalang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LyricsResponse {

    private String[] english;
    private String[] romanized;
    private String[] original;
}
