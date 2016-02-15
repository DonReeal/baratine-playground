package eu.dons.baratine.persistence;

import io.baratine.core.Result;

public interface NamedSequencesService {
    
    public void nextValFor(String key, Result<Long> result);
    void currValFor(String key, Result<Long> result);

}
