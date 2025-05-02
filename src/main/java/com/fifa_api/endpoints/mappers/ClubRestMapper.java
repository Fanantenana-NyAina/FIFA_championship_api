package com.fifa_api.endpoints.mappers;

import com.fifa_api.dao.mappers.ClubMapper;
import com.fifa_api.endpoints.rest.ClubRest;
import com.fifa_api.models.Club;
import com.fifa_api.models.Coach;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ClubRestMapper implements Function<Club, ClubRest> {
    private final CoachRestMapper coachRestMapper;
    private final ClubMapper clubMapper;

    @Override
    public ClubRest apply(Club club) {
        if (club == null) {
            return null;
        }

        return new ClubRest(
                club.getClubId(),
                club.getClubName(),
                club.getClubAcronym(),
                club.getCreationYear(),
                club.getStadium(),
                coachRestMapper.apply(club.getCoach())
        );
    }

    public ClubRest toClubRest(Club club) {
        if (club == null) {
            return null;
        }

        ClubRest clubRest = new ClubRest();
        clubRest.setId(club.getClubId());
        clubRest.setName(club.getClubName());
        clubRest.setAcronym(club.getClubAcronym());
        clubRest.setYearCreation(club.getCreationYear());
        clubRest.setStadium(club.getStadium());
        clubRest.setCoach(coachRestMapper.apply(club.getCoach()));

        return clubRest;
    }

    public Club toClubModel(ClubRest clubRest) {
        if (clubRest == null) {
            return null;
        }

        Club club = new Club();
        club.setClubId(clubRest.getId());
        club.setClubName(clubRest.getName());
        club.setClubAcronym(clubRest.getAcronym());
        club.setCreationYear(clubRest.getYearCreation());
        club.setStadium(clubRest.getStadium());

        Coach coach = coachRestMapper.toCoachModel(clubRest.getCoach());
        club.setCoach(coach);

        return club;
    }
}
