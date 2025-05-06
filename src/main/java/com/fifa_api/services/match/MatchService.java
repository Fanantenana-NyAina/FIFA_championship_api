package com.fifa_api.services.match;

import com.fifa_api.dao.operations.match.MatchCRUDOperation;
import com.fifa_api.models.Status;
import com.fifa_api.models.match.Matches;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchCRUDOperation matchCRUDOperation;

    public List<Matches> findAllMatchesAtSeason(
            String seasonYear,
            Status matchStatus,
            String clubPlayingName,
            LocalDateTime matchAfter,
            LocalDateTime matchBeforeOrEquals
    ) {
        List<Matches> matches = matchCRUDOperation.getAllMatchesAtSeason(seasonYear);

        return matches.stream()
                .filter(m -> matchStatusMatch(m, matchStatus))
                .filter(m -> clubNameMatch(m, clubPlayingName))
                .filter(m -> matchAfterMatch(m, matchAfter))
                .filter(m -> matchBeforeOrEqualsMatch(m, matchBeforeOrEquals))
                .toList();
    }

    private boolean matchStatusMatch(Matches match, Status status) {
        return status == null || match.getStatus() == status;
    }

    private boolean clubNameMatch(Matches match, String clubPlayingName) {
        if (clubPlayingName == null) return true;
        String nameLower = clubPlayingName.toLowerCase();
        return match.getHomeClub().getClubName().toLowerCase().contains(nameLower)
                || match.getAwayClub().getClubName().toLowerCase().contains(nameLower);
    }

    private boolean matchAfterMatch(Matches match, LocalDateTime after) {
        return after == null || match.getMatchDateTime().isAfter(after);
    }

    private boolean matchBeforeOrEqualsMatch(Matches match, LocalDateTime beforeOrEquals) {
        return beforeOrEquals == null || match.getMatchDateTime().isBefore(beforeOrEquals) || !match.getMatchDateTime().isAfter(beforeOrEquals);
    }

}
