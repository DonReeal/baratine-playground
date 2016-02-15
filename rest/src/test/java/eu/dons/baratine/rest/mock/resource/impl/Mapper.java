package eu.dons.baratine.rest.mock.resource.impl;

import eu.dons.baratine.rest.mock.MessagePublic;

public class Mapper {
    
    static MessageData updateData(MessagePublic source, MessageData target) {
        
        System.out.println("source " + source + " -- target: " + target);
        target.setValue(source.getValue());
        return target;
    }
    
    static MessagePublic updatePublicData(MessageData source, MessagePublic target) {
        target.setValue((source != null) ? source.getValue() : null);
        return target;
    }

}
