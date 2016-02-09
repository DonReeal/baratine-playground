package eu.dons.pollbus.vote;

import eu.dons.pollbus.vote.api.VoteDT;
import io.baratine.core.Result;

public interface VotingFacade {
    
    public void get(Result<VoteDT> result);
    
    public void subscribe(/* SessionEvents events */);

}
