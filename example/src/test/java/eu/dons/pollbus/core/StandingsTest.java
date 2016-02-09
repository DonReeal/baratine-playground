package eu.dons.pollbus.core;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import eu.dons.pollbus.core.StringBaseStandings;

public class StandingsTest {
    
    @Test
    public void upvote() {        
        StringBaseStandings s = new StringBaseStandings(mkVotables("A")); 
        
        long a1 = s.upvote("A");
        assertTrue(a1 == 1);        
        
        long a2 = s.upvote("A");
        assertTrue(a2 == 2);        
    }
    
    @Test
    public void upvoteNDownVote() {
        StringBaseStandings s = new StringBaseStandings(mkVotables("A"));
        
        s.upvote("A");
        s.upvote("A");
        s.upvote("A");
        s.upvote("A");
        s.upvote("A");
        
        long a4 = s.downvote("A");
        assertTrue(a4 == 4);
    }
    
    @Test
    public void muitpleKeysCountedDistinct(){
        StringBaseStandings s = new StringBaseStandings(mkVotables("A", "B", "C"));
        
        s.upvote("A");
        
        
    }
    
    private static Set<String> mkVotables(String ... values) {
        Set<String> result = new HashSet<String>();
        for(String s : values)
            result.add(s);
        return result;
    }

}
