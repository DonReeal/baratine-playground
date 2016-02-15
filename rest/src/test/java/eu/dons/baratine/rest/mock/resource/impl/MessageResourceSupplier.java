package eu.dons.baratine.rest.mock.resource.impl;

import java.util.function.Supplier;

import eu.dons.baratine.rest.mock.resource.api.IMessageResource;

public class MessageResourceSupplier implements Supplier<IMessageResource> {

    private final String url;
    private final String value;
    
    public MessageResourceSupplier(String url, String value) {
        this.url = url;
        this.value = value;
    }
    
    
    @Override
    public IMessageResource get() {
        
        MessageData data = new MessageData();
        data.setValue(value);
        
        return MessageResource.builder()
                .messageData(data)
                .url(url)
                .build();
    }

}
