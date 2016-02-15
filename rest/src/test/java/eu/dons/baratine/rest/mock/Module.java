package eu.dons.baratine.rest.mock;

import io.baratine.core.ServiceRef;
import eu.dons.baratine.rest.mock.endpoint.api.IMessagesEndpoint;
import eu.dons.baratine.rest.mock.endpoint.impl.ILuceneReader;
import eu.dons.baratine.rest.mock.endpoint.impl.MessagesEndpointImpl;
import eu.dons.baratine.rest.mock.resource.api.IMessageResource;
import eu.dons.baratine.rest.mock.resource.impl.MessageResource;

public class Module {
    
    
    interface IStaticResource {
        ServiceRef serviceRef();
    }
    
    interface IDynamicResource {
        
    }
    
    public static IMessageResource createMessage(String url) {
        return MessageResource.builder().url(url).build();
    }

    
    public static IMessagesEndpoint createEndpoint(ILuceneReader luceneReader) {        
        return MessagesEndpointImpl.builder()._luceneReader(luceneReader).build();
        
    }
}
