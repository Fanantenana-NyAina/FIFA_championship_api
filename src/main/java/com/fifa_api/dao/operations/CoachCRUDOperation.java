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
    public List<Coach> saveAll(List<Coach> entities) {
        List<Coach> savedCoaches = new ArrayList<>();

        String sql = "insert into joueur (id_entraineur, nom, nationalite, age, id_club)\n" +
                "values (?, ?, ?, ?, ?)\n" +
                "on conflict (id_entraineur) \n" +
                "do update set\n" +
                "    nom = excluded.nom,\n" +
                "    nationalite = excluded.nationalite,\n" +
                "    age = excluded.age,\n" +
                "returning id_entraineur, nom, nationalite, age";

        try (Connection con = datasource.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                for (Coach entityToSave : entities) {
                    ps.setObject(1, entityToSave.getCoachId(), Types.OTHER);
                    ps.setString(2, entityToSave.getCoachName());
                    ps.setString(3, entityToSave.getCoachNationality());
                    ps.setInt(4, entityToSave.getCoachAge());
                    ps.setObject(5, entityToSave.getClubId(), Types.OTHER);

                    ps.addBatch();
                }
                ps.executeBatch();

                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        savedCoaches.add(coachMapper.apply(rs));
                    }
                }

                con.commit();
                return savedCoaches;

            } catch (SQLException e) {
                con.rollback();
                throw new ServerException(e);
            }

        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    @SneakyThrows
    public Coach findByIdClub(UUID idClub) {
        Coach coach = null;
        String sql = "select id_entraineur, nom, nationalite, age, id_club from entraineur where id_club = ?";

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
