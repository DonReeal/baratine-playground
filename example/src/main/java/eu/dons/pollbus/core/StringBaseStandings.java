package eu.dons.pollbus.core;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class StringBaseStandings implements Serializable {
    
    private static final long serialVersionUID = 2118006365141642841L;
    
    private final Map<String, Integer> indexPositions;
    private final long[] standings;

    public final Set<String> getVoteKeys(){
        return Collections.unmodifiableSet(indexPositions.keySet());
    }
        
    public StringBaseStandings(Set<String> votableKeys) {
        this.indexPositions = new HashMap<>();
        int index = 0;
        Iterator<String> itr = votableKeys.iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            indexPositions.put(key, index);
            index++;
        }
        this.standings = new long[indexPositions.size()];
    }

    
    public long upvote(String key) throws IllegalArgumentException {
        return ++standings[checkedGetIndex(key)];
    }
    

    public long downvote(String key) throws IllegalArgumentException {
        return --standings[checkedGetIndex(key)];
    }
    
    
    public long getCount(String key) {
        return checkedGetValue(key);
    }
    
    private Integer checkedGetIndex(String key) {   
        if(!indexPositions.containsKey(key))
            throw new IllegalArgumentException("Standings-Key passed not supported!");
        return  indexPositions.get(key);
    }
    
    private long checkedGetValue(String key) {
        if(!indexPositions.containsKey(key))
            throw new IllegalArgumentException("Standings-Key passed not supported!");
        Integer indexOfKey = indexPositions.get(key);
        return standings[indexOfKey];        
    }
    
}
