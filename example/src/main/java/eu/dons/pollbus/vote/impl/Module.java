package eu.dons.pollbus.vote.impl;

import eu.dons.baratine.persistence.BarURL;
import eu.dons.pollbus.polldef.api.PollDT;
import eu.dons.pollbus.vote.api.Pollvote;

public class Module {
    
    static Pollvote createVoting(BarURL url, PollDT polldef) {
        return new VotingBean(url, polldef, null);
    }

}
