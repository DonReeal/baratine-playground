package eu.dons.pollbus.web.user;

import eu.dons.pollbus.vote.entity.Vote;
import io.baratine.core.Result;

public interface VotingFacade {
    public void get(Result<Vote> result);
    public void subscribe(/* SessionEvents events */);

}