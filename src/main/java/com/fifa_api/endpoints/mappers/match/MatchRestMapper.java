package com.fifa_api.endpoints.mappers.match;

import com.fifa_api.endpoints.rest.matchRest.MatchClub;
import com.fifa_api.endpoints.rest.matchRest.MatchRest;
import com.fifa_api.endpoints.rest.matchRest.PlayerMinimumInfo;
import com.fifa_api.endpoints.rest.matchRest.Scorers;
import com.fifa_api.models.Club;
import com.fifa_api.models.match.Goal;
import com.fifa_api.models.match.Matches;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MatchRestMapper {

    public MatchRest toRest(Matches match) {
        if (match == null) return null;

        return MatchRest.builder()
                .id(match.getIdMatch())
                .stadium(match.getStadium())
                .matchDateTime(match.getMatchDateTime())
                .actualStatus(match.getStatus())
                .clubPlayingHome(toMatchClub(match.getHomeClub(), match.getHomeScore(), match.getGoalsInMatch()))
                .clubPlayingAway(toMatchClub(match.getAwayClub(), match.getAwayScore(), match.getGoalsInMatch()))
                .build();
    }

    private MatchClub toMatchClub(Club club, int score, List<Goal> allGoals) {
        if (club == null) return null;

        List<Scorers> scorers = allGoals.stream()
                .filter(goal -> goal.getClub().getClubId().equals(club.getClubId()))
                .map(this::toScorer)
                .toList();

        return MatchClub.builder()
                .id(club.getClubId())
                .name(club.getClubName())
                .acronym(club.getClubAcronym())
                .score(score)
                .scorers(scorers)
                .build();
    }

    private Scorers toScorer(Goal goal) {
        return Scorers.builder()
                .player(PlayerMinimumInfo.builder()
                        .id(goal.getPlayer().getPlayerId())
                        .name(goal.getPlayer().getPlayerName())
                        .number(goal.getPlayer().getPlayerNumber())
                        .build())
                .minuteOfGoal(goal.getMinutes())
                .ownGoal(goal.getIsOwnGoal())
                .build();
    }
}

