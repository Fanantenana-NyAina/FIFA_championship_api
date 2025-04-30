package com.fifa_api.endpoints.mappers;

import com.fifa_api.endpoints.rest.CoachRest;
import com.fifa_api.models.Coach;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CoachRestMapper implements Function<Coach, CoachRest> {
    @Override
    public CoachRest apply(Coach coach) {
        return new CoachRest(
                coach.getCoachName(),
                coach.getCoachNationality()
        );
    }
}
