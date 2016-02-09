package eu.dons.pollbus.vote.impl;

import io.baratine.core.AfterBatch;
import io.baratine.core.Modify;
import io.baratine.core.OnLoad;
import io.baratine.core.OnSave;
import io.baratine.core.Result;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import eu.dons.baratine.persistence.BarURL;
import eu.dons.baratine.persistence.StoredVal;

import eu.dons.pollbus.core.Standings;
import eu.dons.pollbus.polldef.api.PollDT;
import eu.dons.pollbus.vote.api.StandingsDT;
import eu.dons.pollbus.vote.api.VoteDT;
import eu.dons.pollbus.vote.api.Pollvote;
import eu.dons.pollbus.vote.api.VotingEvents;

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
public class VotingBean implements Pollvote { // votes/live/{_nodehash}/{_123}
   
    public VotingBean(BarURL barURL, PollDT polldef, StoredVal<Standings> standingsStore) {
        
        this.url = barURL;
        
        Set<Integer> answerKeySet = polldef.getAnswers().stream()
                .map(a -> Integer.valueOf(a.getAnswerId()))
                .collect(Collectors.toSet());
        
        this.standings = new Standings(answerKeySet);        
        this.standingsStore = standingsStore;
        
        String votingId = barURL.serviceId();
        Map<Integer, Long> zeroVotesEachAnswer = answerKeySet.stream()
            .collect(Collectors.toMap(
                    answerKey -> answerKey, 
                    answerKey -> Long.valueOf(0L)));
        
        StandingsDT standingsDT = new StandingsDT(votingId, zeroVotesEachAnswer);
        this.voteDT = new VoteDT(votingId, polldef, standingsDT);
        
    }
    
    // ********************************************
    // IDENTITY
    private final BarURL url;
    // --------------------------------------------
    // PUBLIC VIEW
    private final VoteDT voteDT;
    // --------------------------------------------
    // PERSISTENT STATE
    private final StoredVal<Standings> standingsStore;
    private Standings standings;
    // --------------------------------------------    
    // publishing-events-address ( onInit() vs pass in initialized)
    private VotingEvents events;
    // ********************************************
    
    @Override
    public void get(Result<VoteDT> result){
        result.complete(voteDT);        
    }
    
    @Override
    public void getURL(Result<BarURL> result) {
        result.complete(url);
    }
    
    @Override
    @Modify public void vote(int key, Result<Boolean> result) {
        standings.upvote(key);
        update();
        result.complete(true);
    }
    
    private void update() {
        Mapper.updatePublicData(voteDT.getStandings(), standings);
    }

    @OnLoad void onLoad(Result<Boolean> finishedResult) {
       standingsStore.load(standings, finishedResult.from(s -> {
           this.standings = s;
           return true;
       }));
    }
    
    @AfterBatch void publishStandingsAfterBatch() {
        this.events.publishStandings(voteDT.getStandings());
    }

    @OnSave private void onSave() {
        standingsStore.save(standings, ok -> Result.ignore());
    }
}   
    
