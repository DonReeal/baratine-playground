package eu.dons.pollbus.multitenancy.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.pure4j.annotations.immutable.ImmutableValue;
import org.pure4j.immutable.AbstractImmutableValue;

@ImmutableValue
public class Tenant extends AbstractImmutableValue<Tenant> implements Serializable {
    
    public static Tenant empty() {
        return new Tenant(null, null, null);
    }
    
    private static final long serialVersionUID = 1_0_0L;
    
    @NotNull
    private final TenantId id;
    @NotNull
    private final String name;
    @NotNull
    private final DateTime registrationDate;
    
    public Tenant(TenantId id, String name, DateTime registrationDate) {
        this.id = id;
        this.name = name;
        this.registrationDate = registrationDate;
    }

    public TenantId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DateTime getRegistrationDate() {
        return registrationDate;
    }

    @Override
    protected void fields(Visitor v, Tenant other) {
       v.visit(this.id, other.id);        
    }
    
}
