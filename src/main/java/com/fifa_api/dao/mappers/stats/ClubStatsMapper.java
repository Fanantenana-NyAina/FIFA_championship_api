package com.fifa_api.dao.mappers.stats;

import com.fifa_api.dao.operations.ClubCRUDOperation;
import com.fifa_api.dao.operations.SeasonCRUDOperation;
import com.fifa_api.models.stats.ClubStatistics;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ClubStatsMapper implements Function<ResultSet, ClubStatistics> {
    private final ClubCRUDOperation clubCRUDOperation;
    private final SeasonCRUDOperation seasonCRUDOperation;

    // id_stats | id_club | id_saison | points | buts_marques | buts_encaisses | difference_buts | clean_sheets
    @SneakyThrows
    @Override
    public ClubStatistics apply(ResultSet rs) {
        ClubStatistics clubStatistics = new ClubStatistics();

        clubStatistics.setClubStatsId(UUID.fromString(rs.getString("id_stats")));

        UUID clubId = UUID.fromString(rs.getString("id_club"));
        clubStatistics.setClub(clubCRUDOperation.getById(clubId));

        UUID seasonId = UUID.fromString(rs.getString("id_saison"));
        clubStatistics.setSeason(seasonCRUDOperation.getById(seasonId));

        clubStatistics.setRankingPoints(rs.getInt("points"));
        clubStatistics.setScoredGoals(rs.getInt("buts_marques"));
        clubStatistics.setConcededGoals(rs.getInt("buts_encaisses"));
        clubStatistics.setDifferenceGoals(rs.getInt("difference_buts"));
        clubStatistics.setCleanSheetNumber(rs.getInt("clean_sheets"));

        return clubStatistics;
    }
}
