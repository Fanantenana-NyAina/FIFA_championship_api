package com.fifa_api.dao.operations;

import com.fifa_api.dao.DbConnection;
import com.fifa_api.dao.mappers.PlayerMapper;
import com.fifa_api.models.Player;
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
        String sql = "INSERT INTO joueur (id_joueur, nom, numero, poste, nationalite, age) " +
                "VALUES (?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (id_joueur) DO UPDATE SET " +
                "nom = EXCLUDED.nom, " +
                "numero = EXCLUDED.numero, " +
                "poste = EXCLUDED.poste, " +
                "nationalite = EXCLUDED.nationalite, " +
                "age = EXCLUDED.age " +
                "RETURNING id_joueur";

        try (Connection con = datasource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            List<Player> savedPlayers = new ArrayList<>();

            for (Player player : players) {
                // Si nouveau joueur, génère un UUID
                if (player.getPlayerId() == null) {
                    player.setPlayerId(UUID.randomUUID());
                }

                ps.setObject(1, player.getPlayerId(), Types.OTHER);
                ps.setString(2, player.getPlayerName());
                ps.setInt(3, player.getPlayerNumber());
                ps.setObject(4, player.getPost(), Types.OTHER);
                ps.setString(5, player.getPlayerNationality());
                ps.setInt(6, player.getPlayerAge());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        savedPlayers.add(player);
                    }
                }
            }
            return savedPlayers;
        } catch (SQLException e) {
            throw new ServerException(e);
        }
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
