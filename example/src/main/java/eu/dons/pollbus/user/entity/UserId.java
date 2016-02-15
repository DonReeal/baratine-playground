package eu.dons.pollbus.user.entity;

import org.pure4j.annotations.immutable.ImmutableValue;
import org.pure4j.immutable.AbstractImmutableValue;

import eu.dons.pollbus.multitenancy.entity.TenantId;

@ImmutableValue
public class UserId extends AbstractImmutableValue<UserId> {

    private TenantId tenantId;
    private String id;
    
    
    
    @Override
    protected void fields(org.pure4j.immutable.AbstractImmutableValue.Visitor v, UserId other) {
        // TODO Auto-generated method stub
        
    }

}
