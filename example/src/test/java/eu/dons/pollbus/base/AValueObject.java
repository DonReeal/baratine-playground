package eu.dons.pollbus.base;

import eu.dons.pollbus.base.data.immutable.ValueObjectBase;

final class AValueObject extends ValueObjectBase<AValueObject> {
	
	private static final AValueObject EMPTY = new AValueObject("", 0);
	
	private final String stringVal;
	private final Integer integerVal;

	
	public AValueObject(String stringVal, Integer integerVal) {
		this.stringVal = stringVal;
		this.integerVal = integerVal;
	}
	
	@Override
	public AValueObject getEmpty() {
		return EMPTY;
	}

	@Override
	protected void fields(org.pure4j.immutable.AbstractImmutableValue.Visitor v, AValueObject other) {
		v.visit(this.stringVal, other.stringVal);
		v.visit(this.integerVal, other.integerVal);
	}
	
	

	
	

}
