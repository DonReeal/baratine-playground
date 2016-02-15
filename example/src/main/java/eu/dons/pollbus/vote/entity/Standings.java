package eu.dons.pollbus.vote.entity;

import java.io.Serializable;

import org.pure4j.annotations.immutable.ImmutableValue;
import org.pure4j.collections.PersistentHashMap;
import org.pure4j.immutable.AbstractImmutableValue;

@ImmutableValue
public class Standings extends AbstractImmutableValue<Standings> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private PersistentHashMap<Integer, Long> voteCountByChoiceId;
    
    @Override
    protected void fields(Visitor v, Standings other) {
        
    }

    
}
