package eu.dons.pollbus.vote.impl;

import eu.dons.baratine.persistence.BarURL;
import eu.dons.pollbus.vote.api.Pollvote;
import eu.dons.pollbus.vote.api.VoteDT;

public interface PollvoteSync extends Pollvote {
    
    BarURL getURL(BarURL result);
    boolean vote(int key);
    VoteDT get();

}
