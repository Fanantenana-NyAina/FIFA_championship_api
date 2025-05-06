package com.fifa_api.dao.operations.match;

import com.fifa_api.dao.DbConnection;
import com.fifa_api.dao.mappers.match.MatchMapper;
import com.fifa_api.dao.operations.CRUD;
import com.fifa_api.models.match.Matches;
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
public class MatchCRUDOperation implements CRUD<Matches> {
    private final DbConnection datasource;
    private final MatchMapper matchMapper;

    @Override
    public List<Matches> getAll(Integer page, Integer size) {
        return List.of();
    }

    @Override
    public Matches getById(UUID id) {
        return null;
    }

    @Override
    public List<Matches> saveAll(List<Matches> entities) {
        return List.of();
    }

    @SneakyThrows
    public List<Matches> getAllMatchesAtSeason(String seasonYear) {
        List<Matches> matches = new ArrayList<>();

        String sql = """
                select
                    m.id_match,
                    m.date_heure,
                    m.stade,
                    m.id_club_domicile,
                    m.id_club_exterieur,
                    m.score_domicile,
                    m.score_exterieur,
                    m.id_saison,
                    m.status
                from match m
                join saison s on m.id_saison = s.id_saison
                where s.season_year = ?;
                """;

        try (Connection connection = datasource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, seasonYear);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Matches match = matchMapper.apply(rs);
                    matches.add(match);
                }
            }

        }
        return matches;
    }
}
