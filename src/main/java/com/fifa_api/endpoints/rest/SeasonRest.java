package com.fifa_api.endpoints.rest;

import com.fifa_api.models.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeasonRest {
    private Integer year;
    private String alias;
    private UUID id;
    private Status status;
}
