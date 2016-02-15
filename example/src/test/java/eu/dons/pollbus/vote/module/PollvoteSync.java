package eu.dons.pollbus.vote.module;

import eu.dons.baratine.persistence.BarId;
import eu.dons.pollbus.vote.boundary.Pollvote;
import eu.dons.pollbus.vote.entity.Vote;

public interface PollvoteSync extends Pollvote {
    
    BarId getURL(BarId result);
    boolean vote(int key);
    Vote get();

}
