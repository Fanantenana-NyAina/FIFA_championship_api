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
        if (club == null) {
            return null;
        }

        return new ClubRest(
                club.getClubId(),
                club.getClubName(),
                club.getClubAcronym(),
                club.getCreationYear(),
                club.getStadium(),
                club.getCoach() != null ? coachRestMapper.apply(club.getCoach()) : null
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
        clubRest.setCoach(club.getCoach() != null ? coachRestMapper.apply(club.getCoach()) : null);

        return clubRest;
    }
}
