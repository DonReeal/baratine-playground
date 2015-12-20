package eu.dons.baratine.rest.test;

import io.baratine.core.ResultStream;

import java.util.List;

import com.caucho.v5.el.stream.Stream;

import eu.dons.baratine.rest.composite.Endpoint;

/**
 * An Endpoint serving DATA over standard REST-API
 * @author donreeal
 *
 * @param <DATA> the data that is being served by this endpoint
 */
public interface RestEndpointSync<DATA> extends 
		Endpoint.GET<DATA>,
		Endpoint.POST<DATA>,
		Endpoint.PUT<DATA>,
		Endpoint.DELETE {
	
	public List<DATA> get();	
	public String post(DATA data);
	public Stream<DATA> put(Iterable<DATA> resource);
	public void delete();
	
	
}
