package eu.dons.pollbus.polldef.entity;

import javax.validation.constraints.NotNull;

import org.pure4j.immutable.AbstractImmutableValue;

public class PollId extends AbstractImmutableValue<PollId> {
    
    @NotNull
    private final String userId;    
    @NotNull
    private final Long id;
    
    @Override
    protected void fields(Visitor v, PollId other) {
        v.visit(userId, other.userId);
        v.visit(id, other.userId);
    }

    public PollId(String userId, Long id) {
        super();
        this.userId = userId;
        this.id = id;
    }
}
