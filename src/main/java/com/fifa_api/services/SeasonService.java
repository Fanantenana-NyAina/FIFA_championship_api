package com.fifa_api.services;

import com.fifa_api.dao.operations.SeasonCRUDOperation;
import com.fifa_api.models.Season;
import com.fifa_api.services.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonService {
    private final SeasonCRUDOperation seasonCRUDOperation;
    
    public List<Season> getAllSeasons(Integer page, Integer size) {
        try {
            return seasonCRUDOperation.getAll(page, size);
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
