package eu.dons.baratine.rest;

import javax.inject.Inject;

import io.baratine.core.ServiceManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.caucho.junit.RunnerBaratine;

import eu.dons.baratine.rest.mock.MessagePublic;
import eu.dons.baratine.rest.mock.resource.api.IMessageResource;
import eu.dons.baratine.rest.mock.resource.api.IMessageResourceSync;
import eu.dons.baratine.rest.mock.resource.impl.BarURL;
import eu.dons.baratine.rest.mock.resource.impl.MessageResource;

@RunWith(RunnerBaratine.class)
public class RestResourceTest {
    
    @Inject
    ServiceManager sm;
    
    @Test
    public void test() {        

        BarURL url = new BarURL("pod", "test", "message-test-resource");
        
        MessageResource messageResource = MessageResource.builder()
                .url("message-test-resource")
                .build();
        
        IMessageResourceSync service = sm.newService()
                .address(url.val())
                .service(messageResource)
                .build()
                .as(IMessageResourceSync.class);
    
        System.out.println(service.get());
        MessagePublic newValue = new MessagePublic();
        newValue.setValue("DODODO");
        service.update(newValue);
        System.out.println(service.get().toString());
        
    }

}
