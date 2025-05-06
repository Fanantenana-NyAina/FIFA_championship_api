package com.fifa_api.models.match;

import com.fifa_api.models.Club;
import com.fifa_api.models.Season;
import com.fifa_api.models.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Matches {
    private UUID idMatch;
    private LocalDateTime matchDateTime;
    private String stadium;
    private Club homeClub;
    private Club awayClub;
    private int homeScore;
    private int awayScore;
    private Season season;
    private Status status;
    private List<Goal> goalsInMatch = new ArrayList<>();
}

