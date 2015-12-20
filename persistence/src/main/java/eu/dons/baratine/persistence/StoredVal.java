package eu.dons.baratine.persistence;

import io.baratine.core.Result;
import io.baratine.store.Store;

import java.util.Objects;

public class StoredVal<T> {
	
	private final String _key;
	private final Store<T> _store;
	
	public StoredVal(Store<T> store, String valueKey) {
		Objects.requireNonNull(store, "store may not be null!");
		Objects.requireNonNull(valueKey, "valueKey may not be null!");
		
		_store = store;
		_key = valueKey;
	}
	
	/**
	 * Load from store
	 * @param key
	 * @param defaultValue
	 * @param result
	 */
	public void load( T defaultValue, Result<T> result) {
		_store.get(_key, result.from((v, r) -> {
			if(v == null)
				r.complete(defaultValue);
			else
				r.complete(v);
		}));
	}

	public void save(T value, Result<Boolean> result) {
		_store.put(_key, value, result.from(Void -> true));
	}

	public void delete(Result<Boolean> result) {
		_store.remove(_key, result.from(Void -> true));
	}

}
