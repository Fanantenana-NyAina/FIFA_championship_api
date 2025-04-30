package com.fifa_api.dao.operations;

import com.fifa_api.dao.DbConnection;
import com.fifa_api.dao.mappers.ClubMapper;
import com.fifa_api.models.Club;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClubCRUDOperation implements CRUD<Club> {
    private final DbConnection datasource;
    private ClubMapper clubMapper;

    @Override
    public List<Club> getAll(Integer page, Integer size) {
        return List.of();
    }

    @Override
    public Club getById(UUID id) {
        String sql = "select c.id_club, c.nom, c.acronyme, c.annee_creation, c.nom_stade, c.id_championnat " +
                "from club c where c.id_club = ?";

        Club club = null;

        try(Connection con = datasource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id.toString());

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
        return List.of();
    }
}
