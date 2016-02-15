package eu.dons.pollbus.multitenancy.entity;

import java.io.Serializable;

import org.pure4j.annotations.immutable.ImmutableValue;
import org.pure4j.immutable.AbstractImmutableValue;

@ImmutableValue
public class TenantId extends AbstractImmutableValue<TenantId> implements Serializable {
    
    private static final long serialVersionUID = 1_0_0L;
    
    private final String id;
    
    public String getId() {
        return id;
    }

    public TenantId(String id) {
        this.id = id;
    }

    @Override
    protected void fields(Visitor v, TenantId other) {
        v.visit(this.id, other.id);
    }

}
