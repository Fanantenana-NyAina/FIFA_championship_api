package com.fifa_api.services.stats;

import com.fifa_api.dao.operations.ClubCRUDOperation;
import com.fifa_api.dao.operations.stats.ClubStatisticsCRUDOperation;
import com.fifa_api.models.stats.ClubStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubStatisticsService {
    private final ClubStatisticsCRUDOperation clubStatisticsCRUDOperation;

    public List<ClubStatistics> getClubsStatisticsAtSeason(
            String seasonAlias,
            Boolean hasToBeClassified )
    {
        List<ClubStatistics> clubStatistics = clubStatisticsCRUDOperation.getClubStatsBySeasonAlias(seasonAlias);

        if (hasToBeClassified) {
            clubStatistics.sort(Comparator
                    .comparingInt(ClubStatistics::getRankingPoints).reversed()
                    .thenComparingInt(ClubStatistics::getDifferenceGoals).reversed()
                    .thenComparingInt(ClubStatistics::getCleanSheetNumber).reversed());
        } else {
            clubStatistics.sort(Comparator.comparing(
                    clubStatistics1 -> clubStatistics1.getClub().getClubName()));
        }

        return clubStatistics;
    }

}
