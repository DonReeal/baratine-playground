package eu.dons.pollbus.polldef.boundary;

import io.baratine.core.Result;

import java.util.List;

import eu.dons.pollbus.polldef.entity.Choice;
import eu.dons.pollbus.polldef.entity.Poll;

public interface IPollEndpoint {

    void createPoll(String creatorId, String name, String text, List<Choice> choices, Result<Poll> result);

}