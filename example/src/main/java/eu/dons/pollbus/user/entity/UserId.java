package eu.dons.pollbus.user.entity;

import javax.validation.constraints.NotNull;

import org.pure4j.annotations.immutable.ImmutableValue;
import org.pure4j.immutable.AbstractImmutableValue;

import eu.dons.pollbus.base.data.immutable.ValueObjectBase;

@ImmutableValue
public class UserId extends ValueObjectBase<UserId> {
	public transient static final UserId EMPTY = new UserId("");

	@NotNull
    private final String key;
	   
    public String getKey() {
		return key;
	}

	@Override
    protected void fields(Visitor v, UserId other) {
    	v.visit(this.key, other.key);
    }
    
    public UserId(String uuid){
    	this.key = uuid;
    }

	@Override
	public UserId getEmpty() {
		return EMPTY;
	}

}
