package eu.dons.baratine.rest.composite;

import io.baratine.core.Result;

/**
 * A unique resource with standard REST API Interface
 * @author donreeal
 *
 * @param <RESOURCE_DATA> the public data of this resource
 */
public interface Resource {
	
	public interface GET<RESOURCE_DATA> {
		/**
		 * Returns this resources data view
		 * @param result
		 */
		public void get(Result<RESOURCE_DATA> result);
	}
	
	public interface PUT<RESOURCE_DATA> {
		/**
		 * Update this Resource, reflecting all field-values set in RESOURCE_DATA -parameter
		 * @param data
		 * @param result
		 */
		public void put(Result<String> result);
	}
	
	public interface DELETE<RESOURCE_DATA> {
		/**
		 * Delete this Resource
		 * @param result success of delete
		 */
		public void delete(Result<Boolean> result);
	}
	

}
