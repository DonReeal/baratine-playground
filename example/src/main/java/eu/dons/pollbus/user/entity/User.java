package eu.dons.pollbus.user.entity;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.pure4j.annotations.immutable.ImmutableValue;

import eu.dons.pollbus.base.data.immutable.EntityBase;

@ImmutableValue
public class User extends EntityBase<UserId, User> {	
	public transient static final User EMPTY = new User(new UserId(""), "", "");
	
	@Valid
	private final UserId userId;
	
	@Size(min=3)
	private final String name;

	@Size(min=8)
	private final String pwHash;
	
	public User(UserId id, String name, String passwordHash) {
		this.userId = id; 
		this.name = name;
		this.pwHash = passwordHash;
	}
	
	public UserId identity() {
		return userId;
	}

	public String getName() {
		return name;
	}

	@Override
	protected void fields(Visitor v, User other) {
		v.visit(this.userId, other.userId);
	}	

	@Override
	protected void contentFields(org.pure4j.immutable.AbstractImmutableValue.Visitor v, User other) {
		v.visit(this.name, other.name);
	}

	@Override
	public User getEmpty() {
		return EMPTY;
	}


}
