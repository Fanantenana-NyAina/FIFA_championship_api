package com.fifa_api.endpoints;

import com.fifa_api.endpoints.mappers.match.MatchRestMapper;
import com.fifa_api.endpoints.rest.matchRest.MatchRest;
import com.fifa_api.models.Status;
import com.fifa_api.models.match.Matches;
import com.fifa_api.services.match.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchRestController {
    private final MatchService matchService;
    private final MatchRestMapper matchRestMapper;

    @GetMapping("/matches/{seasonYear}")
    public ResponseEntity<Object> findAllMatchesAtSeason(
            @PathVariable String seasonYear,
            @RequestParam(required = false) Status matchStatus,
            @RequestParam(required = false) String clubPlayingName,
            @RequestParam(required = false)LocalDateTime matchAfter,
            @RequestParam(required = false) LocalDateTime matchBeforeOrEquals)
    {
        List<Matches> matches = matchService.findAllMatchesAtSeason(seasonYear, matchStatus, clubPlayingName, matchAfter, matchBeforeOrEquals);

        List<MatchRest> matchesResponses = matches.stream().map(
                matchRestMapper::toRest
        ).toList();

        return ResponseEntity.status(HttpStatus.OK).body(matchesResponses);
    }
}
