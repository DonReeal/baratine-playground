package eu.dons.pollbus.base;

import eu.dons.pollbus.base.data.immutable.EntityBase;

final class AnEntity extends EntityBase<AnEntity> {
	
	private final String id;
	private final String valueOne;
	private final Integer valueTwo;
	
	
	
	public AnEntity(String id, String valueOne, Integer valueTwo) {
		this.id = id;
		this.valueOne = valueOne;
		this.valueTwo = valueTwo;
	}
	
	public String getValueOne() {
		return valueOne;
	}

	public Integer getValueTwo() {
		return valueTwo;
	}
	
	private static final transient AnEntity EMPTY = new AnEntity("", "", 0);
	@Override public AnEntity getEmpty() { return EMPTY; }
	
	
	@Override
	protected void fields(Visitor v, AnEntity other) {
		v.visit(this.id, other.id);
		
	}
	
	@Override
	protected void contentFields(Visitor v, AnEntity other) {
		v.visit(this.valueOne, other.valueOne);
		v.visit(this.valueTwo, other.valueTwo);			
	}

	

	
	
}
