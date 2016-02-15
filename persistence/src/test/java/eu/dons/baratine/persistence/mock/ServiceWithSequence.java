package eu.dons.baratine.persistence.mock;

import eu.dons.baratine.persistence.BarId;
import eu.dons.baratine.persistence.Module;
import eu.dons.baratine.persistence.Sequence;
import eu.dons.baratine.persistence.StoredVal;
import io.baratine.core.OnInit;
import io.baratine.core.Result;
import io.baratine.core.ServiceManager;
import io.baratine.store.Store;

public class ServiceWithSequence {
    
    public ServiceWithSequence(BarId url) {
        this.serviceURL = url;
    }    
    
    private final BarId serviceURL;
    private Sequence seq;
    
    public void incrementSequence(Result<Long> result) {
        seq.nextVal(result);
    }
    
    @OnInit
    public void onInit() {
        
        String storageAdress = BarId.toStorage(serviceURL).fqURL();
        Store<Long> store = ServiceManager.current().lookup(storageAdress).as(Store.class, Long.class);
        StoredVal<Long> storage = new StoredVal<Long>(store, "current-count");
        
        seq = Module.createSequence(storage);
   }
    
}
