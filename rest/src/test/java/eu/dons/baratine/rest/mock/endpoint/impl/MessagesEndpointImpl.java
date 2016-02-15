package eu.dons.baratine.rest.mock.endpoint.impl;

import io.baratine.core.Result;
import io.baratine.core.ResultStream;
import io.baratine.core.Service;
import io.baratine.core.ServiceManager;
import io.baratine.core.ServiceRef;
import io.baratine.stream.ResultStreamBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Builder;
import eu.dons.baratine.rest.mock.MessagePublic;
import eu.dons.baratine.rest.mock.endpoint.api.IMessagesEndpoint;
import eu.dons.baratine.rest.mock.resource.api.IMessageResource;
import eu.dons.baratine.rest.mock.resource.impl.MessageResourceSupplier;

@Builder
@Service("public:///messages")
public class MessagesEndpointImpl implements IMessagesEndpoint {

    private ILuceneReader _luceneReader;
    private long count = 0L;
    
    private HashMap<Long, ServiceRef> _managedResources = new HashMap<>();
    
    
    @Override
    public void get(Result<MessagePublic[]> messages) {
        
        // analog BIG DATA ... bringing functions to the data
      ResultStreamBuilder<String> searchAlgorithm = _luceneReader.search("all", "");
      // ============================================================================================
      searchAlgorithm
          .collect(ArrayList::new,
              (initializedListOnRemote, remoteEntry) -> initializedListOnRemote.add(remoteEntry),
              (localList, remoteList) -> localList.addAll(remoteList))
          .result(messages.from(l -> l.toArray(new MessagePublic[l.size()])));      
    }
    
    
 

    // move to the respective owner (per user / per company etc)
    // this should be done by the hashedValue of the url 
    // owner means the managing partition
    // 
    private void getPerNodeSimulation(List<String> urls, Result<List<MessagePublic>> r) {
        List<MessagePublic> result = new ArrayList<>();
        urls.forEach(url -> {
            MessagePublic mp = new MessagePublic();
            mp.setUrl(url);
            mp.setValue("valueOf: <" + url + ">");
            result.add(mp);
        });
        r.complete(result);
    }

    @Override
    public void delete() {
        
        _luceneReader.search("all", "")
            .forEach((url, innerResult) -> {
                IMessageResource msg = ServiceManager.current().lookup("public:///messages" + url).as(IMessageResource.class);
                msg.delete(innerResult);
        });
                
    }

    @Override
    public void post(MessagePublic newMessage, Result<String> result) {
        
        String relativeURL = "/" + Long.toString(++ count);
        String fqURL = "public:///messages" + relativeURL;
 
        IMessageResource messageResource = 
                ServiceManager.current().newService()
                .address(fqURL)
                .service(new MessageResourceSupplier(fqURL, newMessage.getValue()))
                .build().as(IMessageResource.class);
             
        messageResource.get(result.from(mr -> mr.getUrl()));
        
        // afterBatch -> publish to Lucene?
    }


    @Override
    public void put(List<MessagePublic> messages, ResultStream<MessagePublic> result) {
        // TODO Auto-generated method stub
        
    }

}
