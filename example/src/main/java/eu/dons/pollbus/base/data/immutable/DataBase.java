package eu.dons.pollbus.base.data.immutable;

import org.pure4j.immutable.AbstractImmutableValue;

public abstract class DataBase<T> extends AbstractImmutableValue<T> {
	
	public abstract T getEmpty();

}
