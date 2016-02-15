package eu.dons.pollbus.vote.module;

import static org.junit.Assert.fail;
import io.baratine.core.ServiceManager;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pure4j.collections.PersistentList;

import com.caucho.junit.RunnerBaratine;

import eu.dons.pollbus.polldef.entity.Choice;
import eu.dons.pollbus.polldef.entity.Poll;
import eu.dons.pollbus.polldef.entity.PollId;

@RunWith(RunnerBaratine.class)
public class ModuleTest {

    @Inject private ServiceManager _sm;
    
    PollvoteSync service;
    private Poll poll;

    @Test
    public void aNewVoteShouldBeFullySelfexplanatory() {
        fail("");
    }

    private void creatingPollData() {
        
        PollId pollId = new PollId("don", 1L);
        poll = new Poll(pollId, "pollName", "pollText", new PersistentList<Choice>());
    
    }

}
