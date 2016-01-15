package eu.dons.baratine.persistence;

import io.baratine.core.Result;

public interface Sequence {
	
	public void nextVal(Result<Long> result);
	void currVal(Result<Long> result);
	
}
