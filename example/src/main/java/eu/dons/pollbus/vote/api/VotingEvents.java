package eu.dons.pollbus.vote.api;


/**
 * VotingBean guarantees to send the following methods
 * 
 * @author donreeal
 *
 */
public interface VotingEvents {
    public void publishStandings(StandingsDT result);
}
