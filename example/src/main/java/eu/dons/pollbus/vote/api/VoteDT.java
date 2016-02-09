package eu.dons.pollbus.vote.api;

import lombok.Data;
import eu.dons.pollbus.polldef.api.PollDT;

@Data
public class VoteDT {
    
    private final String voteId;
    private final PollDT polldef;
    private final StandingsDT standings;
    
}
