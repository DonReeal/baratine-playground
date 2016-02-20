package eu.dons.pollbus.vote.control;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class StandingsSupport implements Serializable {
    
    private static final long serialVersionUID = 2118006365141642841L;
    
    private final Map<Integer, Integer> indexPositions;
    private final long[] standings;

    public final Set<Integer> getVoteKeys(){
        return Collections.unmodifiableSet(indexPositions.keySet());
    }
        
    public StandingsSupport(Set<Integer> votableKeys) {
        this.indexPositions = new HashMap<>();
        int index = 0;
        Iterator<Integer> itr = votableKeys.iterator();
        while (itr.hasNext()) {
            Integer key = itr.next();
            indexPositions.put(key, index);
            index++;
        }
        this.standings = new long[indexPositions.size()];
    }
    
    public long upvote(Integer key) throws IllegalArgumentException {
        return ++standings[checkedGetIndex(key)];
    }
    
    public long downvote(Integer key) throws IllegalArgumentException {
        return --standings[checkedGetIndex(key)];
    }    
    
    public long getCount(Integer key) {
        return checkedGetValue(key);
    }
    
    private Integer checkedGetIndex(Integer key) {   
        if(!indexPositions.containsKey(key))
            throw new IllegalArgumentException("Standings-Key passed not supported!");
        return  indexPositions.get(key);
    }
    
    private long checkedGetValue(Integer key) {
        if(!indexPositions.containsKey(key))
            throw new IllegalArgumentException("Standings-Key passed not supported!");
        Integer indexOfKey = indexPositions.get(key);
        return standings[indexOfKey];        
    }
    
}
