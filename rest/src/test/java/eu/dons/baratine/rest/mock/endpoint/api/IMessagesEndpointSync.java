package eu.dons.baratine.rest.mock.endpoint.api;

import java.util.List;

import eu.dons.baratine.rest.mock.MessagePublic;

public interface IMessagesEndpointSync extends IMessagesEndpoint {
    
    public MessagePublic[] get();
    public void delete();
    public String post(MessagePublic resource);
    
    public List<MessagePublic> put(List<MessagePublic> messages);

}
