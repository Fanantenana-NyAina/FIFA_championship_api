package com.fifa_api.endpoints.mappers;

import com.fifa_api.endpoints.rest.ClubRest;
import com.fifa_api.models.Club;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ClubRestMapper implements Function<Club, ClubRest> {
    private final CoachRestMapper coachRestMapper;

    @Override
    public ClubRest apply(Club club) {
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
        ClubRest clubRest = new ClubRest();

        clubRest.setId(club.getClubId());
        clubRest.setName(club.getClubName());
        clubRest.setAcronym(club.getClubAcronym());
        clubRest.setYearCreation(club.getCreationYear());
        clubRest.setStadium(club.getStadium());
        clubRest.setCoach(coachRestMapper.apply(club.getCoach()));

        return clubRest;
    }
}
