package com.fifa_api.dao.operations;

import com.fifa_api.dao.DbConnection;
import com.fifa_api.dao.mappers.SeasonMapper;
import com.fifa_api.models.Club;
import com.fifa_api.models.Season;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SeasonCRUDOperation implements CRUD<Season> {
    private final DbConnection datasource;
    private final SeasonMapper seasonMapper;

    @SneakyThrows
    @Override
    public List<Season> getAll(Integer page, Integer size) {
        List<Season> seasons = new ArrayList<>();

        String sql = "select id_saison, year, season_year, status " +
                "from saison limit ? offset ?";

        try(Connection con = datasource.getConnection();
            PreparedStatement ps = con.prepareCall(sql)) {
            ps.setInt(1, size);
            ps.setInt(2, (page-1)*size);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Season s =  seasonMapper.apply(rs);
                    seasons.add(s);
                }

                return seasons;
            }
        }
    }

    @SneakyThrows
    @Override
    public Season getById(UUID id) {
        Season season = null;

        String sql = "select id_saison, year, season_year, status " +
                "from saison where id_saison = ?";

        try(Connection con = datasource.getConnection();
            PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, id, Types.OTHER);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Season s =  seasonMapper.apply(rs);
                    season = s;
                }

                return season;
            }
        }
    }

    @Override
    public List<Season> saveAll(List<Season> entities) {
        return List.of();
    }
}
