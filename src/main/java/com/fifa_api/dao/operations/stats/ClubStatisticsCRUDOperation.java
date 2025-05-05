package com.fifa_api.dao.operations.stats;

import com.fifa_api.dao.DbConnection;
import com.fifa_api.dao.mappers.stats.ClubStatsMapper;
import com.fifa_api.dao.operations.CRUD;
import com.fifa_api.models.stats.ClubStatistics;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClubStatisticsCRUDOperation implements CRUD<ClubStatistics> {
    private final DbConnection dbConnection;
    private final ClubStatsMapper clubStatsMapper;

    @Override
    public List<ClubStatistics> getAll(Integer page, Integer size) {
        return List.of();
    }

    @Override
    public ClubStatistics getById(UUID id) {
        return null;
    }

    @Override
    public List<ClubStatistics> saveAll(List<ClubStatistics> entities) {
        return List.of();
    }

    @SneakyThrows
    public List<ClubStatistics> getClubStatsBySeasonAlias(String seasonAlias) {
        List<ClubStatistics> clubStatisticsList = new ArrayList<>();

        String sql = """
                select\s
                  scs.id_stats,
                  scs.id_club,
                  scs.id_saison,
                  scs.points,
                  scs.buts_marques,
                  scs.buts_encaisses,
                  scs.difference_buts,
                  scs.clean_sheets
                from stats_club_saison scs
                join saison s on scs.id_saison = s.id_saison
                where s.season_year = ?;
                """;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, seasonAlias);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClubStatistics clubStatistics = clubStatsMapper.apply(rs);
                    clubStatisticsList.add(clubStatistics);
                }

                return clubStatisticsList;
            }
        }
    }
}
