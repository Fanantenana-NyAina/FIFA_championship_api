package com.fifa_api.services;

import com.fifa_api.dao.operations.ClubCRUDOperation;
import com.fifa_api.models.Club;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubCRUDOperation clubCRUDOperation;

    public List<Club> getAllClubs(Integer page, Integer size) {
        try {
            return clubCRUDOperation.getAll(page, size);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Club> saveAllClubs(List<Club> clubs) {
        return clubCRUDOperation.saveAll(clubs);
    }
}
