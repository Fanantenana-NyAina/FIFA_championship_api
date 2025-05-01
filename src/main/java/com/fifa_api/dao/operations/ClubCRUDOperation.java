package com.fifa_api.dao.operations;

import com.fifa_api.dao.DbConnection;
import com.fifa_api.dao.mappers.ClubMapper;
import com.fifa_api.models.Club;
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
public class ClubCRUDOperation implements CRUD<Club> {
    private final DbConnection datasource;
    private final ClubMapper clubMapper;
    private final CoachCRUDOperation coachCRUDOperation;

    @SneakyThrows
    @Override
    public List<Club> getAll(Integer page, Integer size) {
        List<Club> clubs = new ArrayList<>();

        String sql = "select id_club, nom, acronyme, annee_creation, nom_stade, id_championnat" +
                " from club limit ? offset ?";

        try(Connection con = datasource.getConnection();
            PreparedStatement ps = con.prepareCall(sql)) {
            ps.setInt(1, size);
            ps.setInt(2, (page-1)*size);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Club cb =  clubMapper.apply(rs);
                    clubs.add(cb);
                }

                return clubs;
            }
        }
    }

    @Override
    public Club getById(UUID id) {
        Club club = null;

        String sql = "select id_club, nom, acronyme, annee_creation, nom_stade, id_championnat " +
                "from club where id_club = ?";

        try(Connection con = datasource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id, Types.OTHER);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    club = clubMapper.apply(rs);
                }

                return club;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Club> saveAll(List<Club> entities) {
        List<Club> savedClubs = new ArrayList<>();

        String sql = "insert into club (id_club, nom, acronyme, annee_creation, nom_stade, id_championnat)\n" +
                "values (?, ?, ?, ?, ?, ?)\n" +
                "on conflict (id_club) \n" +
                "do update set\n" +
                "    nom = excluded.nom,\n" +
                "    acronyme = excluded.acronyme,\n" +
                "    annee_creation = excluded.annee_creation,\n" +
                "    nom_stade = excluded.nom_stade,\n" +
                "    id_championnat = excluded.id_championnat\n" +
                "returning id_club, nom, acronyme, annee_creation, nom_stade, id_championnat";

        try (Connection con = datasource.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                for (Club entityToSave : entities) {
                    ps.setObject(1, entityToSave.getClubId(), Types.OTHER);
                    ps.setString(2, entityToSave.getClubName());
                    ps.setString(3, entityToSave.getClubAcronym());
                    ps.setInt(4,entityToSave.getCreationYear());
                    ps.setString(5, entityToSave.getStadium());
                    ps.setObject(6, entityToSave.getChampionshipId(), Types.OTHER);
                    ps.addBatch();

                    coachCRUDOperation.saveAll(List.of(entityToSave.getCoach()));
                }
                ps.executeBatch();

                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        savedClubs.add(clubMapper.apply(rs));
                    }
                }

                con.commit();
                return savedClubs;

            } catch (SQLException e) {
                con.rollback();
                throw new ServerException(e);
            }

        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }
}
