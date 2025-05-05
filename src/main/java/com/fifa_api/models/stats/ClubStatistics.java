package com.fifa_api.models.stats;

import com.fifa_api.models.Club;
import com.fifa_api.models.Season;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClubStatistics {
    private UUID clubStatsId;
    private Club club;
    private Season season;
    private int rankingPoints;
    private int scoredGoals;
    private int concededGoals;
    private int differenceGoals;
    private int cleanSheetNumber;
}
