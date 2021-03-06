package eu.dons.pollbus.base.data.immutable;

import org.pure4j.Pure4J;
import org.pure4j.immutable.AbstractImmutableValue;

import eu.dons.pollbus.base.data.Entity;

public  abstract class EntityBase<ID, E extends Entity<ID>> extends DataBase<E> implements Entity<ID> {

	private static class EqualsVisitor implements Visitor {		
		boolean result = true;
		@Override
		public void visit(Object o, Object o2) {
			if (result != false) {
				result = Pure4J.equals(o, o2);
			}
		}
	}
	
	protected abstract void contentFields(Visitor v, E other);

	
	@Override @SuppressWarnings("unchecked")
	public boolean contentEquals(Object other) {
		if (other == null) {
			return false;
		} else if (other == this) {
			return true;
		} else if (this.getClass().isInstance(other)) {	
			final EqualsVisitor v = new EqualsVisitor();
			contentFields(v, (E) other);
			return v.result;
			
		} else {
			return false;
		}		
	}


	@Override
	public boolean isEmpty() {
		return this.equals(getEmpty()) & this.contentEquals(getEmpty()) ;
	}


}
