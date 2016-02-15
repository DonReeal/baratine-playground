package eu.dons.baratine.rest;


/**
 * A unique resource with standard REST API Interface
 * @author donreeal
 * @param <RESOURCE_DATA>
 *
 * @param <RESOURCE_DATA> the public data of this resource
 */
public interface RestResource<R> extends 
		ResourceAPI.GET<R>,
		ResourceAPI.PUT<R>,
		ResourceAPI.DELETE<R> {
}
