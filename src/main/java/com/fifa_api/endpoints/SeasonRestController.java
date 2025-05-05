package com.fifa_api.endpoints;

import com.fifa_api.endpoints.mappers.SeasonRestMapper;
import com.fifa_api.endpoints.rest.SeasonRest;
import com.fifa_api.models.Season;
import com.fifa_api.services.SeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SeasonRestController {
    private final SeasonService seasonService;
    private final SeasonRestMapper seasonRestMapper;

    @GetMapping("/seasons")
    public ResponseEntity<Object> getSeasons(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    ) {
        List<Season> seasons = seasonService.getAllSeasons(page, size);
        List<SeasonRest> seasonRests = seasons.stream().map(
                seasonRestMapper
        ).toList();

        return ResponseEntity.status(HttpStatus.OK).body(seasonRests);
    }
}
