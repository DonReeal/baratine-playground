package eu.dons.pollbus.core.validation.test;

import javax.validation.constraints.NotNull;

import org.pure4j.immutable.AbstractImmutableValue;

public class AggregateId extends AbstractImmutableValue<AggregateId> {
    
    @NotNull
    private final String userId;    
    @NotNull
    private final Long id;
    
    @Override
    protected void fields(Visitor v, AggregateId other) {
        v.visit(userId, other.userId);
        v.visit(id, other.userId);
    }

    public AggregateId(String userId, Long id) {
        super();
        this.userId = userId;
        this.id = id;
    }
}
