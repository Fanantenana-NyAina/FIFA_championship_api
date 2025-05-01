package com.fifa_api.endpoints;

import com.fifa_api.endpoints.mappers.ClubRestMapper;
import com.fifa_api.endpoints.rest.ClubRest;
import com.fifa_api.models.Club;
import com.fifa_api.services.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClubRestController {
    private final ClubService clubService;
    private final ClubRestMapper clubRestMapper;

    @GetMapping("/clubs")
    public ResponseEntity<Object> getClubs(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    ) {
        List<Club> clubs = clubService.getAllClubs(page, size);
        List<ClubRest> clubsRest = clubs.stream().map(
                clubRestMapper::toClubRest
        ).toList();

        return ResponseEntity.status(HttpStatus.OK).body(clubsRest);
    }
}
