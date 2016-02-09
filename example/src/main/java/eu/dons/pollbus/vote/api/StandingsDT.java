package eu.dons.pollbus.vote.api;

import java.util.Map;

import lombok.Data;
import eu.dons.pollbus.core.StringBaseStandings;

@Data
public class StandingsDT {
    
    private final String voteId;
    private final Map<Integer, Long> votesByAnswer;
    
}
