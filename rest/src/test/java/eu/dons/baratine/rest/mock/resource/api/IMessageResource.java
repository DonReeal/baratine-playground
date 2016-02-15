package eu.dons.baratine.rest.mock.resource.api;

import eu.dons.baratine.rest.mock.MessagePublic;
import io.baratine.core.Result;

public interface IMessageResource {
    
    public void get(Result<MessagePublic> result);
    public void update(MessagePublic m, Result<Void> result);
    public void delete(Result<Void> result);

}
