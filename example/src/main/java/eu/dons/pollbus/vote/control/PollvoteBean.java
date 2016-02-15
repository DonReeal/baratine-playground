package eu.dons.pollbus.vote.control;

import io.baratine.core.AfterBatch;
import io.baratine.core.Modify;
import io.baratine.core.OnLoad;
import io.baratine.core.OnSave;
import io.baratine.core.Result;

import java.util.Set;
import java.util.stream.Collectors;

import eu.dons.baratine.persistence.BarId;
import eu.dons.baratine.persistence.StoredVal;
import eu.dons.pollbus.polldef.entity.Poll;
import eu.dons.pollbus.vote.boundary.Pollvote;
import eu.dons.pollbus.vote.boundary.VotingEvents;

/**
 * Publishes at "event://{_podId}/vote/{_voteId}"
 * 
 * e.g. a needs to register to this address to receive updates  
 * 
 *     
 *  @Journal works together with 
 *  @OnSave and 
 *  @Modify to add reliability, 
 * 
 *  by saving method calls before they are executed, 
 *  
 *  and replaying them after a server crash.
 *  
 *
 *
 * 
 * @author donreeal
 *
 */
public class PollvoteBean implements Pollvote { // votes/live/{_nodehash}/{_123}
   
    public PollvoteBean(BarId voteResourceURL, Poll poll) {
        
        this.url = voteResourceURL;     // technical identity
        this.poll = poll;               // logical identity
        
        Set<Integer> answerKeySet = poll.getChoices().stream()
                .map(a -> Integer.valueOf(a.getId()))
                .collect(Collectors.toSet());
        
        this.standings = new StandingsSupport(answerKeySet);
    }
    
    // ********************************************
    // IDENTITY
    private final BarId url;
    private final Poll poll;
    // --------------------------------------------
    // PUBLIC VIEW
    // move: private final VoteDT voteDT;
    // --------------------------------------------
    // PERSISTENT STATE
    private StoredVal<StandingsSupport> standingsStore;
    private StandingsSupport standings;
    // --------------------------------------------    
    // publishing-events-address ( onInit() vs pass in initialized)
    private VotingEvents events;
    // ********************************************
    
    
    
    @Override
    public void getURL(Result<BarId> result) {
        result.complete(url);
    }
    
    @Override
    @Modify public void vote(int key, Result<Boolean> result) {
        standings.upvote(key);
        result.complete(true);
    }
    
    @OnLoad void onLoad(Result<Boolean> finishedResult) {
       standingsStore.load(standings, finishedResult.from(s -> {
           this.standings = s;
           return true;
       }));
    }
    
    @AfterBatch void publishStandingsAfterBatch() {
        // this.events.publishStandings(voteDT.getStandings());
    }

    @OnSave void onSave() {
        standingsStore.save(standings, ok -> Result.ignore());
    }

    @Override
    public void get(Result<Poll> result) {
        
    }
}   
    
