package com.fifa_api.dao.operations;

import com.fifa_api.dao.DbConnection;
import com.fifa_api.dao.mappers.PlayerMapper;
import com.fifa_api.models.Player;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.*;
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

        String sql = "select id_joueur, nom, numero, poste, nationalite, age, id_club\n" +
                "from joueur limit ? offset ?";

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
        Player player = null;
        String sql = "select id_joueur, nom, numero, poste, nationalite, age, id_club " +
                "from joueur where id_joueur = ?";

        try(Connection con = datasource.getConnection();
            PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, id, Types.OTHER);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    player =  playerMapper.apply(rs);
                }

                return player;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Player> saveAll(List<Player> players) {
        String sql = "insert into joueur (id_joueur, nom, numero, poste, nationalite, age, id_club)\n" +
                "values (?, ?, ?, ?, ?, ?, ?)\n" +
                "on conflict (id_joueur) \n" +
                "do update set\n" +
                "    nom = excluded.nom,\n" +
                "    numero = excluded.numero,\n" +
                "    poste = excluded.poste,\n" +
                "    nationalite = excluded.nationalite,\n" +
                "    age = excluded.age\n" +
                "returning id_joueur, nom, numero, poste, nationalite, age";

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SneakyThrows
    public List<Player> finPlayersByIdClub(UUID idClub) {
        List<Player> players = new ArrayList<>();

        String sql = "select id_joueur, nom, numero, poste, nationalite, age, id_club\n" +
                "from joueur where id_club = ?";

        try(Connection con = datasource.getConnection();
            PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, idClub, Types.OTHER);

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
}
