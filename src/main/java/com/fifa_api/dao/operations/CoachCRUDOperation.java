package com.fifa_api.dao.operations;

import com.fifa_api.dao.DbConnection;
import com.fifa_api.dao.mappers.CoachMapper;
import com.fifa_api.models.Coach;
import com.fifa_api.services.exceptions.ServerException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.*;
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

        String sql = "select id_entraineur, nom, nationalite, id_club \n" +
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
        String sql = "select id_entraineur, nom, nationalite from entraineur where id_entraineur = ?";

        try(Connection con = datasource.getConnection();
            PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, idClub, Types.OTHER);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    coach =  coachMapper.apply(rs);
                }

                return coach;
            }
        }
    }

    @Override
    public List<Coach> saveAll(List<Coach> coaches) {
        String sql = "insert into entraineur (id_entraineur, nom, nationalite, id_club) " +
                "values (?, ?, ?, ?) " +
                "on conflict (id_entraineur) do update set " +
                "nom = EXCLUDED.nom, " +
                "nationalite = EXCLUDED.nationalite, " +
                "id_club = EXCLUDED.id_club";

        try (Connection con = datasource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (Coach coach : coaches) {
                if (coach.getCoachId() == null) {
                    coach.setCoachId(UUID.randomUUID());
                }

                ps.setObject(1, coach.getCoachId(), Types.OTHER);
                ps.setString(2, coach.getCoachName());
                ps.setString(3, coach.getCoachNationality());
                ps.setObject(4, coach.getClubId(), Types.OTHER);
                ps.addBatch();
            }

            ps.executeBatch();
            return coaches;
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    @SneakyThrows
    public Coach findByIdClub(UUID idClub) {
        Coach coach = null;
        String sql = "select id_entraineur, nom, nationalite, id_club from entraineur where id_club = ?";

        try(Connection con = datasource.getConnection();
            PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, idClub, Types.OTHER);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    coach =  coachMapper.apply(rs);
                }

                return coach;
            }
        }
    }
}
