package eu.dons.baratine.rest.mock.resource.api;

import eu.dons.baratine.rest.mock.MessagePublic;

public interface IMessageResourceSync extends IMessageResource {
    
    public MessagePublic get();
    public void update(MessagePublic m);
    public void delete();

}
