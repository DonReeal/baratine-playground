package eu.dons.pollbus.vote.entity;

import org.pure4j.annotations.immutable.ImmutableValue;
import org.pure4j.immutable.AbstractImmutableValue;

@ImmutableValue
public class Vote extends AbstractImmutableValue<Vote> {
    
    private VoteId id;
    
    @Override
    protected void fields(Visitor v, Vote other) {
        v.visit(this.id, other.id);
    }

}
