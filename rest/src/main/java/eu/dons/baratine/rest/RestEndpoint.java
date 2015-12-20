package eu.dons.baratine.rest;

import eu.dons.baratine.rest.composite.Endpoint;

/**
 * An Endpoint serving DATA over standard REST-API
 * @author donreeal
 *
 * @param <DATA> the data that is being served by this endpoint
 */
public interface RestEndpoint<DATA> extends 
		Endpoint.GET<DATA>,
		Endpoint.POST<DATA>,
		Endpoint.PUT<DATA>,
		Endpoint.DELETE {	
}
