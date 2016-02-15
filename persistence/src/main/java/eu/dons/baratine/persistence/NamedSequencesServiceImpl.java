package eu.dons.baratine.persistence;

import io.baratine.core.Modify;
import io.baratine.core.OnInit;
import io.baratine.core.OnLoad;
import io.baratine.core.OnSave;
import io.baratine.core.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class NamedSequencesServiceImpl implements NamedSequencesService {
    
    private static final Logger LOG = Logger.getLogger(SequenceServiceImpl.class.getName());
    
    private String owner; // mandantId
    
    private StoredVal<Map<String, Long>> _countStore;
    private Map<String, Long> _count = new HashMap<>();

    
    @Modify @Override
    public void nextValFor(String key, Result<Long> result) {
        Long nextVal = _count.get(key) + 1;
        _count.put(key, nextVal);
        result.complete(nextVal);   
    }

    @Override
    public void currValFor(String key, Result<Long> result) {
        result.complete(_count.get(key));
    }

    
    /* initializing fields - can it be done with DI? */
    public void setStore(StoredVal<Map<String, Long>> storage) {
        Objects.requireNonNull(storage, "storage may not be null!");
        _countStore = storage;
    }
    
    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    @OnInit
    public void onInit() {
        Objects.requireNonNull( _countStore, "Store not setup yet - use setStore to initialize this instance correctly.");
    }
    
    @OnLoad
    public void onLoad(Result<Boolean> result) {
        LOG.fine("@Onload - synchronizing counted value from Store");
        _countStore.load(new HashMap<String, Long>(), x -> {
            this._count = x;
            result.complete(true);
        });
    }
    

    @OnSave
    public void onSave() {
        LOG.fine("onSave called!");
        _countStore.save(_count, x -> {
            if(!x)
                LOG.severe("SAVING WENT WRONG!!");
        });
    }
    

}
