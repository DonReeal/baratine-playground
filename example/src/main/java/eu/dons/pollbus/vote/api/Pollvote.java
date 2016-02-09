package eu.dons.pollbus.vote.api;

import io.baratine.core.Result;
import eu.dons.baratine.persistence.BarURL;

public interface Pollvote {

    void getURL(Result<BarURL> result);
    void vote(int key, Result<Boolean> result);
    void get(Result<VoteDT> result);

}