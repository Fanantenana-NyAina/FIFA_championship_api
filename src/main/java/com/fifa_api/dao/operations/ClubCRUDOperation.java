package com.fifa_api.dao.operations;

import com.fifa_api.dao.DbConnection;
import com.fifa_api.dao.mappers.ClubMapper;
import com.fifa_api.models.Club;
import com.fifa_api.models.Coach;
import com.fifa_api.services.exceptions.ServerException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public List<Club> saveAll(List<Club> clubs) {
        List<Coach> coaches = clubs.stream()
                .map(Club::getCoach)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        coachCRUDOperation.saveAll(coaches);

        String sql = "insert into club (id_club, nom, acronyme, annee_creation, nom_stade, id_championnat) " +
                "values (?, ?, ?, ?, ?, ?) " +
                "on conflict (id_club) DO update set " +
                "nom = excluded.nom, " +
                "acronyme = excluded.acronyme, " +
                "annee_creation = excluded.annee_creation, " +
                "nom_stade = excluded.nom_stade, " +
                "id_championnat = excluded.id_championnat";

        try (Connection con = datasource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (Club club : clubs) {
                if (club.getClubId() == null) {
                    club.setClubId(UUID.randomUUID());
                }

                ps.setObject(1, club.getClubId(), Types.OTHER);
                ps.setString(2, club.getClubName());
                ps.setString(3, club.getClubAcronym());
                ps.setInt(4, club.getCreationYear());
                ps.setString(5, club.getStadium());
                ps.setObject(6, club.getChampionshipId(), Types.OTHER);
                ps.addBatch();
            }

            ps.executeBatch();
            return clubs;
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }
}
