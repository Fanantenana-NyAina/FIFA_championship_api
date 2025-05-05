package com.fifa_api.dao.operations.stats;

import com.fifa_api.dao.DbConnection;
import com.fifa_api.dao.mappers.stats.PlayerStatsMapper;
import com.fifa_api.dao.operations.CRUD;
import com.fifa_api.models.stats.PlayerStatistics;
import com.fifa_api.services.exceptions.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PlayersStatisticsCRUDOperation implements CRUD<PlayerStatistics> {
    private final DbConnection datasource;
    private final PlayerStatsMapper playerStatsMapper;

    @Override
    public List<PlayerStatistics> getAll(Integer page, Integer size) {
        return List.of();
    }

    @Override
    public PlayerStatistics getById(UUID id) {
        return null;
    }

    @Override
    public List<PlayerStatistics> saveAll(List<PlayerStatistics> entities) {
        return List.of();
    }

    public PlayerStatistics findByPlayerAndSeason(UUID playerId, String seasonYear) {

        String sql = """
            select s.id_stats, s.id_joueur, s.id_saison,\s
                   s.buts_marques, s.buts_contre_son_camp,\s
                   s.duree_jeu_value, s.duree_jeu_unit
            from stats_joueur_saison s
            join saison sa on s.id_saison = sa.id_saison
            where s.id_joueur = ? and sa.season_year = ?
           \s""";

        try (Connection con = datasource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, playerId, Types.OTHER);
            ps.setString(2, seasonYear);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return playerStatsMapper.apply(rs);
                }
            }
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }
}
