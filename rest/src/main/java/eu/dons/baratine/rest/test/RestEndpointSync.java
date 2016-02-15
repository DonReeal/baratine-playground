package eu.dons.baratine.rest.test;

import io.baratine.core.ResultStream;

import java.util.List;

import eu.dons.baratine.rest.EndpointAPI;

/**
 * An Endpoint serving DATA over standard REST-API
 * @author donreeal
 *
 * @param <DATA> the data that is being served by this endpoint
 */
public interface RestEndpointSync<DATA> extends 
		EndpointAPI.GET<DATA>,
		EndpointAPI.POST<DATA>,
		EndpointAPI.PUT<DATA>,
		EndpointAPI.DELETE {
	
	public List<DATA> get();	
	public String post(DATA data);
	public ResultStream<DATA> put(Iterable<DATA> resource);
	public void delete();
	
	
}
