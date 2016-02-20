package eu.dons.pollbus.base.data.immutable;

import eu.dons.pollbus.base.data.ValueObject;

public abstract class ValueObjectBase<ID_TYPE> extends DataBase<ID_TYPE> implements ValueObject {

	@Override
	public boolean isEmpty() {
		return this.equals(getEmpty());
	}


}
