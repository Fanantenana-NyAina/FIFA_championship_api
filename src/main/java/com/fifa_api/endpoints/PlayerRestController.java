package com.fifa_api.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerRestController {

    @GetMapping("/players")
    public ResponseEntity<Object> getPlayers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer ageMinimum,
            @RequestParam(required = false) Integer ageMaximum,
            @RequestParam(required = false) String clubName
    ) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
