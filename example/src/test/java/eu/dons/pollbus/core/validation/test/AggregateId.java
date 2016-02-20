package eu.dons.pollbus.core.validation.test;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.pure4j.immutable.AbstractImmutableValue;

public class AggregateId extends AbstractImmutableValue<AggregateId> {
    
    @NotNull @Size(min=4)
    private final String key;    

    @Override
    protected void fields(Visitor v, AggregateId other) {
        v.visit(key, other.key);
    }

    public AggregateId(String key) {
        super();
        this.key = key;
    }
}
