package eu.dons.baratine.rest;

import eu.dons.baratine.rest.composite.Resource;

/**
 * A unique resource with standard REST API Interface
 * @author donreeal
 * @param <RESOURCE_DATA>
 *
 * @param <RESOURCE_DATA> the public data of this resource
 */
public interface RestResource<R> extends 
		Resource.GET<R>,
		Resource.PUT<R>,
		Resource.DELETE<R> {
}
