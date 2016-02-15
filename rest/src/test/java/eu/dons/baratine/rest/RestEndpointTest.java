package eu.dons.baratine.rest;

import static org.junit.Assert.assertTrue;
import io.baratine.core.Lookup;
import io.baratine.core.ResultStream;
import io.baratine.core.ServiceManager;
import io.baratine.core.ServiceRef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.caucho.junit.ConfigurationBaratine;
import com.caucho.junit.RunnerBaratine;

import eu.dons.baratine.rest.mock.MessagePublic;
import eu.dons.baratine.rest.mock.Module;
import eu.dons.baratine.rest.mock.endpoint.api.IMessagesEndpoint;
import eu.dons.baratine.rest.mock.endpoint.api.IMessagesEndpointSync;
import eu.dons.baratine.rest.mock.endpoint.impl.ILuceneReader;
import eu.dons.baratine.rest.mock.endpoint.impl.LuceneReaderMock;
import eu.dons.baratine.rest.mock.endpoint.impl.MessagesEndpointImpl;
import eu.dons.baratine.rest.mock.endpoint.inmem.IM_MessagesEndpoint;
import eu.dons.baratine.rest.mock.endpoint.inmem.Message;
import eu.dons.baratine.rest.mock.resource.api.IMessageResource;
import eu.dons.baratine.rest.mock.resource.api.IMessageResourceSync;
import eu.dons.baratine.rest.test.RestEndpointSync;

@RunWith(RunnerBaratine.class)
@ConfigurationBaratine(services = { MessagesEndpointImpl.class })
public class RestEndpointTest {

    private static final List<String> ALICES_MESSAGES = Arrays.asList("I AM ALICE", "LIVING IN ALICES WORLD");
    private static final List<String> BOBS_MESSAGES = Arrays.asList("I AM BOB", "LIVING IN BOBS WORLD");

    // @Inject @Lookup("/messages")
    // IMessagesEndpointSync _service;
    //
    @Inject
    ServiceManager _sm;

    IMessagesEndpointSync _service;

    @Test
    public void testPost() {

        // SETUP MODULE
        ILuceneReader luceneMock = LuceneReaderMock.builder()
                ._indexedValue("alice", ALICES_MESSAGES)
                ._indexedValue("bob", BOBS_MESSAGES).build();
        
        ILuceneReader luceneService = _sm.newService()
                .address("/lucene-mock/lucene-reader")
                .service(luceneMock).build().as(ILuceneReader.class);
        
        IMessagesEndpoint msgEndpoint = Module.createEndpoint(luceneService);
       
        // ref
        ServiceRef ref = _sm.newService()
                .address("/test/messages")
                .service(msgEndpoint)
                .build();
        
        _service = ref.as(IMessagesEndpointSync.class);

        
        
        // =====================================================================
        System.out.println(String.format("==== POSTING_A_MESSAGE (Endpoint {%s}) ::: ", ref.getAddress()));
        MessagePublic m = new MessagePublic();
        m.setValue("hello");
        String url = _service.post(m);
        
        System.out.println("Returned URL for first Message created: " + url);

        // =====================================================================
        System.out.println("=== LOOKUP_DYNAMICALLY_CREATED_RESOURCE ::: ");
        IMessageResourceSync messageResource = _sm.lookup(url).as(IMessageResourceSync.class);
        System.out.println("GET: " + messageResource.get());
        
    }

    @Test
    public void testUpdate() throws InterruptedException {

        _service.delete();
        System.out.println("Cleared content of endpoint - get yields: " + _service.get());

        MessagePublic m1In = new MessagePublic();
        String v1 = "value.1";
        m1In.setValue(v1);
        _service.post(m1In);

        MessagePublic m2In = new MessagePublic();
        String v2 = "value.2";
        m2In.setValue(v2);
        _service.post(m2In);

        MessagePublic[] messages = _service.get();

        MessagePublic m1 = getByValue(messages, v1);
        System.out.println("Found message one: " + m1);
        MessagePublic m2 = getByValue(messages, v2);
        System.out.println("Found message two: " + m2);
        System.out.println(messages);

        String url1 = m1.getUrl();
        String value1Updated = m1.getValue() + m1.getValue();
        m1.setValue(value1Updated);
        String url2 = m2.getUrl();
        String value2Updated = m2.getValue() + m2.getValue();
        m2.setValue(value2Updated);

        List<MessagePublic> updateMessageList = new ArrayList<MessagePublic>();
        updateMessageList.add(m1);
        updateMessageList.add(m2);

        ResultStream<MessagePublic> stream = new ResultStream<MessagePublic>() {
            private static final long serialVersionUID = 1L;

            @Override
            public void accept(MessagePublic m) {
                System.out.println("m.url=" + m.getUrl() + " processed!");
            }

            @Override
            public void complete() {
                System.out.println("processing complete!");
            }
        };

        _service.put(updateMessageList, stream);
        Thread.sleep(5000);

        MessagePublic[] updated = _service.get();
        System.out.println(updated);
        MessagePublic m1Updated = getByValue(updated, value1Updated);
        assertTrue(m1Updated.getUrl().equals(url1));
        MessagePublic m2Updated = getByValue(updated, value2Updated);
        assertTrue(m2Updated.getUrl().equals(url2));

    }
    
    private static MessagePublic getByValue(MessagePublic[] messages, String matching) {
        for(MessagePublic m : messages) {
            if( matching.equals(m.getValue())){
                return m;
            }
        }
        return null;
    }

    private static MessagePublic getByValue(Collection<MessagePublic> messages, String v1) {
        Optional<MessagePublic> first = messages.stream()
                .filter(m -> v1.equals(m.getValue()))
                .findFirst();
        assertTrue(first.isPresent());
        return first.get();
    }

}
