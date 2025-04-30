package com.fifa_api.dao.operations;

import com.fifa_api.dao.DbConnection;
import com.fifa_api.dao.mappers.CoachMapper;
import com.fifa_api.models.Coach;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CoachCRUDOperation implements CRUD<Coach> {
    private final DbConnection datasource;
    private final CoachMapper coachMapper;

    @Override
    public List<Coach> getAll(Integer page, Integer size) {
        List<Coach> coaches = new ArrayList<>();

        String sql = "select id_entraineur, nom, nationalite, age, id_club \n" +
                "from entraineur limit ? offset ?";

        try(Connection con = datasource.getConnection();
            PreparedStatement ps = con.prepareCall(sql)) {
            ps.setInt(1, size);
            ps.setInt(2, (page-1)*size);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Coach c =  coachMapper.apply(rs);
                    coaches.add(c);
                }

                return coaches;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    public Coach getById(UUID idClub) {
        Coach coach = null;
        String sql = "select id_entraineur, nom, nationalite, age from entraineur where id_entraineur = ?";

        try(Connection con = datasource.getConnection();
            PreparedStatement ps = con.prepareCall(sql)) {
            ps.setString(1, idClub.toString());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    coach =  coachMapper.apply(rs);
                }

                return coach;
            }
        }
    }

    @Override
    public List<Coach> saveAll(List<Coach> entities) {
        return List.of();
    }

    @SneakyThrows
    public Coach findByIdClub(UUID idClub) {
        Coach coach = null;
        String sql = "select id_entraineur, nom, nationalite, age from entraineur where id_club = ?";

        try(Connection con = datasource.getConnection();
            PreparedStatement ps = con.prepareCall(sql)) {
            ps.setString(1, idClub.toString());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    coach =  coachMapper.apply(rs);
                }

                return coach;
            }
        }
    }
}
