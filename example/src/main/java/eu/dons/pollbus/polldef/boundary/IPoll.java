package eu.dons.pollbus.polldef.boundary;

import io.baratine.core.Result;
import eu.dons.pollbus.polldef.entity.Poll;

public interface IPoll {

    void get(Result<Poll> result);

    void put(Poll data, Result<String> result);

    void delete(Result<Boolean> result);

}