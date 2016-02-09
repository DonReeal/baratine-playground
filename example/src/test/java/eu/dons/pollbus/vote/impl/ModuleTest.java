package eu.dons.pollbus.vote.impl;

import io.baratine.core.ServiceManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.caucho.junit.RunnerBaratine;

import eu.dons.baratine.persistence.BarURL;
import eu.dons.pollbus.polldef.api.AnswerDT;
import eu.dons.pollbus.polldef.api.PollDT;
import eu.dons.pollbus.vote.api.Pollvote;
import eu.dons.pollbus.vote.api.VoteDT;

@RunWith(RunnerBaratine.class)
public class ModuleTest {
    
    @Inject
    private ServiceManager _sm;
    private PollDT polldef;
    
    @Test public void aNewVoteShouldBeFullySelfexplanatory() {
        
        // given
        creatingPollData();
        
        // when 
        BarURL barURL = new BarURL("public", "test-pod", "votes/123");
        Pollvote empty = Module.createVoting(barURL, polldef);
        
        
        PollvoteSync as = _sm.newService().address(barURL.fqURL()).service(empty).build().as(PollvoteSync.class);
        
        // Then
        VoteDT voteDT = as.get();
        System.out.println(voteDT);
        
    }

    private void creatingPollData() {
        
        polldef = new PollDT("123");
        polldef.setName("whos-better-at-this?");
        
        List<AnswerDT> answers = new ArrayList<>();
        AnswerDT me = createAnswerDT(1, "Me" , 1);
        AnswerDT you = createAnswerDT(2, "You" , 2);
        answers.add(me);
        answers.add(you);
        
        polldef.setAnswers(answers);
        
        
    }

    private AnswerDT createAnswerDT(int answerId, String name, int sortKey) {
        AnswerDT a = new AnswerDT();
        a.setAnswerId(answerId);
        a.setName("Me");
        a.setSortKey(sortKey);
        return a;
        
    }

}
