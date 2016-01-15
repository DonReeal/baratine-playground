package eu.dons.baratine.persistence;

import io.baratine.core.Modify;
import io.baratine.core.OnInit;
import io.baratine.core.OnLoad;
import io.baratine.core.OnSave;
import io.baratine.core.Result;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * 
 * Usage:
 * 
 * ServiceManager sm = ServiceManager.current();
 * SequenceServiceImpl seq = new SequenceServiceImpl();
 * seq.setStore(sm.lookup("store://userpod/userids").as(Store.class, Long.class));
 * _ids = sm.newService().service(seq).build().bind("pod://userpod/userids").as(IDSequence.class);
 * 
 * @author donreeal
 *
 */
class SequenceServiceImpl implements Sequence {
	
	private static final Logger LOG = Logger.getLogger(SequenceServiceImpl.class.getName());
	
	private StoredVal<Long> _countStore;
	private long _count = 0L;
	
	/* initializing fields - can it be done with DI? */
	public void setStore(StoredVal<Long> storage) {
        Objects.requireNonNull(storage, "storage may not be null!");
        _countStore = storage;
    }	
	
	@OnInit
	public void onInit() {
	    Objects.requireNonNull( _countStore, "Store not setup yet - use setStore to initialize this instance correctly.");
	}
	
	@OnLoad
	public void onLoad(Result<Boolean> result) {
		LOG.fine("@Onload - synchronizing count value from Store");
		_countStore.load(0L, result.from(x -> true));
	}
	
	@Modify
	@Override
	public void nextVal(Result<Long> result) {	    
	    LOG.fine("nextVal called!");
		result.complete(++_count);
	}

	@OnSave
	public void onSave() {
	    LOG.fine("onSave called!");
		_countStore.save(_count, x -> Result.ignore());
	}
	
	@Override
	public void currVal(Result<Long> result) {
		result.complete(_count);
	}
}
