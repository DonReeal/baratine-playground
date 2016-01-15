package eu.dons.baratine.persistence.mock;

import io.baratine.core.Result;

public interface IServiceWithSequence {
    
    public void incrementSequence(Result<Long> result);

}
