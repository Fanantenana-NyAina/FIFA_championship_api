package com.fifa_api.dao.operations;

import com.fifa_api.dao.DbConnection;
import com.fifa_api.dao.mappers.PlayerMapper;
import com.fifa_api.models.Player;
import lombok.RequiredArgsConstructor;
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
public class PlayerCRUDOperation implements CRUD<Player> {
    private final DbConnection datasource;
    private final PlayerMapper playerMapper;

    @Override
    public List<Player> getAll(Integer page, Integer size) {
        List<Player> players = new ArrayList<>();

        String sql = "select j.id_joueur, j.nom, j.numero, j.poste, j.nationalite, j.age,\n" +
                "c.id_club, c.nom, c.acronyme, c.annee_creation, c.nom_stade,\n" +
                "e.id_entraineur ,e.nom, e.nationalite\n" +
                "from joueur j \n" +
                "join club c on j.id_club = c.id_club\n" +
                "join entraineur e on c.id_club = e.id_club\n" +
                "group by j.id_joueur" +
                "limit ? offset ?;";

        try(Connection con = datasource.getConnection();
            PreparedStatement ps = con.prepareCall(sql)) {
            ps.setInt(1, size);
            ps.setInt(2, (page-1)*size);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Player p =  playerMapper.apply(rs);
                    players.add(p);
                }

                return players;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Player getById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Player> saveAll(List<Player> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
