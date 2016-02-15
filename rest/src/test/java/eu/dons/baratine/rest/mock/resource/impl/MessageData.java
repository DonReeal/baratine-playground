package eu.dons.baratine.rest.mock.resource.impl;

import java.io.Serializable;

public class MessageData implements Serializable {
    
    private static final long serialVersionUID = -49987266113458497L;
    
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MessageData [value=" + value + "]";
    }    

}
