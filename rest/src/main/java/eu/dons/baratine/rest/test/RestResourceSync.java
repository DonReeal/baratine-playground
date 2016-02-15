package eu.dons.baratine.rest.test;

import eu.dons.baratine.rest.ResourceAPI;

/**
 * A unique resource with standard REST API Interface
 * @author donreeal
 * @param <RESOURCE_DATA>
 *
 * @param <RESOURCE_DATA> the public data of this resource
 */
public interface RestResourceSync<R> extends 
		ResourceAPI.GET<R>,
		ResourceAPI.PUT<R>,
		ResourceAPI.DELETE<R> {
    
    public R get();
    public String put(R data);
    public boolean delete();
    

}
