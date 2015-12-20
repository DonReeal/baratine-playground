package eu.dons.baratine.rest.composite;

import io.baratine.core.Result;
import io.baratine.core.ResultStream;

import java.util.List;

public interface Endpoint {
	
	
	/**
	 * GET ALL DATA
	 * @author donreeal
	 *
	 * @param <DATA>
	 */
	public interface GET<DATA> {
		/**
		 * Returns a list of DATA - can be all Data that is being served by this endpoint.
		 * Does not make sens thouhg for huge amounts of data (TODO)
		 * 
		 * @param result
		 */
		public void get(Result<List<DATA>> result);

	}
	
	/**
	 * CREATE NEW DATA ENTRY
	 * @author donreeal
	 *
	 * @param <DATA>
	 */
	public interface POST<DATA> {
		/**
		 * Create a new resource managed by this endpoint.
		 * Returns a unique URL-String that the new resource can be looked up with.
		 * 
		 * @param resource
		 * @param the unique url of the created DATA
		 */
		public void post(DATA resource, Result<String> result);

	}
	
	/**
	 * BULK UPDATING
	 * @author donreeal
	 *
	 * @param <DATA>
	 */
	public interface PUT<DATA> {
		/**
		 * Bulk update of all passed DATA
		 * 
		 * @param resource
		 * @param result
		 */
		public void put(Iterable<DATA> resource, ResultStream<DATA> result);
	}
	
	
	/**
	 * BULK DELETE FUNCTION
	 * @author donreeal
	 *
	 */
	public interface DELETE {
		/**
		 * Bulk delete all DATA owned by this Endpoint
		 */
		public void delete();
	}

}
