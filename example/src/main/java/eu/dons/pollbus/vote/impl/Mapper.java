package eu.dons.pollbus.vote.impl;

import java.util.Map;

import eu.dons.pollbus.core.Standings;
import eu.dons.pollbus.vote.api.StandingsDT;
import eu.dons.pollbus.vote.api.VoteDT;

public class Mapper {

    static void updatePublicData(StandingsDT target, Standings source) {
        Map<Integer, Long> targetValues = target.getVotesByAnswer();
        targetValues.clear();
        for (Integer voteKey : source.getVoteKeys()) {
            targetValues.put(voteKey, source.getCount(voteKey));
        }
    }
    
}
