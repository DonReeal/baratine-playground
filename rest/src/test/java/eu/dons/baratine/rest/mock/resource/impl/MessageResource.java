package eu.dons.baratine.rest.mock.resource.impl;

import lombok.Builder;
import io.baratine.core.Result;
import eu.dons.baratine.rest.mock.MessagePublic;
import eu.dons.baratine.rest.mock.resource.api.IMessageResource;

@Builder
public class MessageResource implements IMessageResource {
    
    private String url = null;
    private MessageData messageData;
    
    @Override
    public void get(Result<MessagePublic> result) {
        result.complete(Mapper.updatePublicData(messageData, new MessagePublic(url)));
    }

    @Override
    public void update(MessagePublic data, Result<Void> result) {
        System.out.println("updating " + data.toString());
        
        MessageData md = messageData;
        if(messageData == null) {
            md = new MessageData();
        }
        messageData = Mapper.updateData(data, md);
        result.complete(null);
    }

    @Override
    public void delete(Result<Void> result) {
        messageData = null;
        result.complete(null);
    }
}
