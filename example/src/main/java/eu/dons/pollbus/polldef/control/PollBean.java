package eu.dons.pollbus.polldef.control;

import io.baratine.core.Result;
import eu.dons.pollbus.polldef.boundary.IPoll;
import eu.dons.pollbus.polldef.entity.Poll;

// @Service("local/{mandant}/{userid}/{pollId}"
public class PollBean implements IPoll {

    private Poll poll;


    @Override
    public void get(Result<Poll> result) {
        result.complete(poll);
    }

    @Override
    public void put(Poll data, Result<String> result) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(Result<Boolean> result) {
        // TODO Auto-generated method stub

    }

}
