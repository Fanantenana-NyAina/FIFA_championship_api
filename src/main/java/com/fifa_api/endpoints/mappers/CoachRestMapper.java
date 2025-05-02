package com.fifa_api.endpoints.mappers;

import com.fifa_api.endpoints.rest.CoachRest;
import com.fifa_api.models.Coach;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CoachRestMapper implements Function<Coach, CoachRest> {
    @Override
    public CoachRest apply(Coach coach) {
        if (coach == null) {
            return null;
        }
        return new CoachRest(
                coach.getCoachName(),
                coach.getCoachNationality()
        );
    }

    public  Coach toCoachModel(CoachRest coachRest) {
        String coachName = coachRest.getName();
        String coachNationality = coachRest.getNationality();

        Coach coach = new Coach();
        coach.setCoachName(coachName);
        coach.setCoachNationality(coachNationality);

        return coach;
    }
}
