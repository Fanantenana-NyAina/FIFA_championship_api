package com.fifa_api.dao.operations.match;

import com.fifa_api.dao.DbConnection;
import com.fifa_api.dao.mappers.match.GoalMapper;
import com.fifa_api.dao.operations.CRUD;
import com.fifa_api.models.match.Goal;
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
public class GoalCRUDOperation implements CRUD<Goal> {
    private final DbConnection datasource;
    private final GoalMapper goalMapper;

    @Override
    public List<Goal> getAll(Integer page, Integer size) {
        return List.of();
    }

    @Override
    public Goal getById(UUID id) {
        return null;
    }

    @Override
    public List<Goal> saveAll(List<Goal> entities) {
        return List.of();
    }

    @SneakyThrows
    public  List<Goal> getPlayerGoals(UUID playerId) {
        List<Goal> goals = new ArrayList<>();

        String sql = """
                select\s
                    b.id_but,
                    b.id_match,
                    b.id_joueur,
                    b.id_club,
                    b.minute,
                    b.contre_son_camp
                from\s
                    but b
                join\s
                    joueur j ON b.id_joueur = j.id_joueur
                where\s
                    b.id_joueur = ?
                """;

        try(Connection connection = datasource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setObject(1, playerId, Types.OTHER);

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Goal goal = goalMapper.apply(rs);
                    goals.add(goal);
                }
            }
        }
        return goals;
    }

    @SneakyThrows
    public List<Goal> getGoalsInMatch(UUID matchId) {
        List<Goal> goals = new ArrayList<>();

        String sql = """
                select\s
                    b.id_but,
                    b.id_match,
                    b.id_joueur,
                    b.id_club,
                    b.minute,
                    b.contre_son_camp
                    from
                        but b
                    join\s
                        match m on b.id_match = m.id_match
                    where
                        m.id_match = ?;
                """;

        try(Connection connection = datasource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setObject(1, matchId, Types.OTHER);

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Goal goal = goalMapper.apply(rs);
                    goals.add(goal);
                }
            }
        }
        return goals;
    }
}

