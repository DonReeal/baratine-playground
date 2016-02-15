package eu.dons.pollbus.vote.boundary;

import io.baratine.core.Result;
import eu.dons.baratine.persistence.BarId;
import eu.dons.pollbus.polldef.entity.Poll;

public interface Pollvote {

    void getURL(Result<BarId> result);
    void vote(int key, Result<Boolean> result);
    void get(Result<Poll> result);

}