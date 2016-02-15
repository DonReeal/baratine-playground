package eu.dons.baratine.rest;


/**
 * An Endpoint serving DATA over standard REST-API
 * @author donreeal
 *
 * @param <DATA> the data that is being served by this endpoint
 */
public interface RestEndpoint<DATA> extends 
		EndpointAPI.GET<DATA>,
		EndpointAPI.POST<DATA>,
		EndpointAPI.PUT<DATA>,
		EndpointAPI.DELETE {	
}
