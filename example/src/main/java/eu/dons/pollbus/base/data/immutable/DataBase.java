package eu.dons.pollbus.base.data.immutable;

import org.pure4j.immutable.AbstractImmutableValue;

public abstract class DataBase<DATA> extends AbstractImmutableValue<DATA> {
	
	public abstract DATA getEmpty();

}
