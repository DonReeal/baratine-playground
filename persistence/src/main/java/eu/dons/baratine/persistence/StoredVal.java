package eu.dons.baratine.persistence;

import io.baratine.core.Result;
import io.baratine.store.Store;

import java.util.Objects;
import java.util.logging.Logger;

public class StoredVal<T> {
    
    private final Logger LOG = Logger.getLogger(StoredVal.class.getName());
	
	private final String _key;
	private final Store<T> _store;
	
	public StoredVal(Store<T> store, String valueKey) {
	    
		Objects.requireNonNull(store, "store may not be null!");
		Objects.requireNonNull(valueKey, "valueKey may not be null!");
		
		_store = store;
		_key = valueKey;
	}
	
	/**
	 * Load from store - returns the default value passed for convenience
	 * @param key
	 * @param defaultValue
	 * @param result
	 */
	public void load(T defaultValue, Result<T> result) {
	    LOG.fine(String.format("<<< LOAD >>> Loading value from store: %s @key: %s", _store, _key));
		_store.get(_key, result.from((v, r) -> {
			if(v == null) {
			    LOG.fine(String.format("<<< END LOAD >>> No value previously saved in this store %s@key: %s", _store, _key ));
				r.complete(defaultValue);
			}
			else {
			    LOG.fine(String.format("<<< END LOAD >>> Value loaded from store: %s", v));
				r.complete(v);
			}
		}));
	}
	
	public void save(T value, Result<Boolean> result) {
	    LOG.fine(String.format("<<< SAVE >>> Saving value: %s to store: %s @key: %s", value.toString(),  _store, _key));
		_store.put(_key, value, result.from(Void -> true));
	}

	public void delete(Result<Boolean> result) {
	    LOG.fine(String.format("<<< DELETE >>> Deleting from store: %s @key: %s", _store, _key));
		_store.remove(_key, result.from(Void -> true));
	}
	
}
