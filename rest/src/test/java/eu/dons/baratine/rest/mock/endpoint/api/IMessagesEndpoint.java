package eu.dons.baratine.rest.mock.endpoint.api;

import io.baratine.core.Result;
import io.baratine.core.ResultStream;

import java.util.List;

import eu.dons.baratine.rest.mock.MessagePublic;

/**
 * 
 * @author donreeal
 *
 * All messages grouped by an owner <- privacy, usecase [MY_MESSAGES, FRIENDS_MESSAGES]
 * 
 *
 */
public interface IMessagesEndpoint {
    
    //public void get(Result<List<MessagePublic>> result);    
    void get(Result<MessagePublic[]> result);
    void delete();
    void post(MessagePublic resource, Result<String> result);
    void put(List<MessagePublic> messages, ResultStream<MessagePublic> result);
    // void get(Result<List<String>> result);
    
    

}
