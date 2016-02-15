package eu.dons.pollbus.vote.boundary;

import eu.dons.pollbus.vote.entity.Standings;


/**
 * VotingBean guarantees to send the following methods
 * 
 * @author donreeal
 *
 */
public interface VotingEvents {
    public void publishStandings(Standings result);
}
