package eu.dons.pollbus.vote.entity;

import javax.validation.constraints.NotNull;

import org.pure4j.annotations.immutable.ImmutableValue;
import org.pure4j.immutable.AbstractImmutableValue;

import eu.dons.pollbus.polldef.entity.PollId;

@ImmutableValue
public class VoteId extends AbstractImmutableValue<VoteId>{
    
    @NotNull
    private final PollId pollId;
    
    @NotNull
    private final Long id;
    
    public PollId getPollId() {
        return pollId;
    }
    
    public Long getId() {
        return id;
    }
    
    public VoteId(PollId pollId, Long id) {
        this.pollId = pollId;
        this.id = id;
    }

    @Override
    protected void fields(org.pure4j.immutable.AbstractImmutableValue.Visitor v, VoteId other) {
        v.visit(this.pollId, other.pollId);
        v.visit(this.id, other.id);
    }
    
}
