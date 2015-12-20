package eu.dons.baratine.persistence;

import io.baratine.core.Result;

public interface IDSequence {
	
	public void getNextId(Result<Long> result);
	void getCurrentId(Result<Long> result);
	
}
