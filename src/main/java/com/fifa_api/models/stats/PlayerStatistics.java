package com.fifa_api.models.stats;

import com.fifa_api.models.Player;
import com.fifa_api.models.Season;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlayerStatistics {
    private UUID playerStatsId;
    private Player player;
    private Season season;
    private int goalsScored;
    private int ownGoals;
    private int playTimeValue;
    private DurationUnit duration;
}
