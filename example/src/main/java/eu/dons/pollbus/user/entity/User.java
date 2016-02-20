package eu.dons.pollbus.user.entity;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.pure4j.annotations.immutable.ImmutableValue;

import eu.dons.pollbus.base.data.immutable.EntityBase;

@ImmutableValue
public class User extends EntityBase<User> {
	
	public static final User EMPTY = new User(new UserId(""), "");
	
	@Valid
	private final UserId id;
	
	@Size(min=3)
	private final String name;
	
	public User(UserId id, String name) {
		this.id = id; 
		this.name = name;
	}

	
	public UserId identity() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	protected void fields(Visitor v, User other) {
		v.visit(this.id, other.id);
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
