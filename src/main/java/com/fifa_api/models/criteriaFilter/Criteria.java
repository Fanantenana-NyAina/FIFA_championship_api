package com.fifa_api.models.criteriaFilter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Criteria {
    private String column;
    private Object value;
}
