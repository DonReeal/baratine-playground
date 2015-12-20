package eu.dons.baratine.persistence;

import io.baratine.core.Modify;
import io.baratine.core.OnInit;
import io.baratine.core.OnLoad;
import io.baratine.core.OnSave;
import io.baratine.core.Result;
import io.baratine.core.ServiceManager;
import io.baratine.store.Store;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * Usage:
 * 
 * ServiceManager sm = ServiceManager.current();
 * IDSequenceBarBean seq = new IDSequenceBarBean();
 * seq.setStore(sm.lookup("store://userpod/userids").as(Store.class, Long.class));
 * _ids = sm.newService().service(seq).build().bind("pod://userpod/userids").as(IDSequence.class);
 * 
 * @author donreeal
 *
 */
public class CountingSequence implements IDSequence {
	
	private final Logger LOG = Logger.getLogger(this.getClass().getName());
	private static final String COUNT_VALUE_KEY = "_count";
	
	private StoredVal<Long> _countStore;
	private long _count = 0L;
	
	@SuppressWarnings("unchecked")
	@OnInit
	public void onInit(){
		
		if(_countStore == null ) {
			LOG.log(Level.SEVERE, "Count store was null - using default url for store-backend. For production setup a store explicitly!");
			ServiceManager sm = ServiceManager.current();
			@SuppressWarnings("rawtypes")
			Store store= sm.lookup("store:///globalsequence").as(Store.class, Long.class);
			_countStore = new StoredVal<Long>(store, COUNT_VALUE_KEY);
			_count = 0L;
		}
	}
	
	@OnLoad
	public void onLoad(Result<Boolean> result) {
		LOG.info("@Onload - synchronizing count value from Store");
		_countStore.load(0L, result.from(x -> true));
	}
	
	public void setStore(Store<Long> store) {
		Objects.requireNonNull(store, "store may not be null!");
		this._countStore = new StoredVal<Long>(store, COUNT_VALUE_KEY);
	}
	
	@Modify
	@Override
	public void getNextId(Result<Long> result) {
		result.complete(++_count);
	}

	@OnSave
	public void doSave(){
		_countStore.save(_count, x -> Result.ignore());
	}
	
	@Override
	public void getCurrentId(Result<Long> result){
		result.complete(_count);
	}
	
}
