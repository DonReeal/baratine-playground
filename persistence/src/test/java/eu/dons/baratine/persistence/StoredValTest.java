package eu.dons.baratine.persistence;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.baratine.core.Lookup;
import io.baratine.core.ResultFuture;
import io.baratine.core.ServiceManager;
import io.baratine.store.Store;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.caucho.junit.ConfigurationBaratine;
import com.caucho.junit.ConfigurationBaratine.Log;
import com.caucho.junit.RunnerBaratine;

import eu.dons.baratine.persistence.mock.ServiceWithSequence;

@RunWith(RunnerBaratine.class)
@ConfigurationBaratine(
        services={ServiceWithSequence.class},
        logLevel="FINE", //logNames={"eu.dons.baratine"}, 
        logs={@Log(name="eu.dons.baratine.persistence", level="FINEST")}
)
public class StoredValTest {

    @Inject
    ServiceManager _sm;
    
    
    private StoredVal<Long> longStore;

    @Test
    public void storedValueReturnsDefaultUnlessPersisted() {
        
        creatingLongStore("persisted-value-is-restored-on-lookup");
        
        Long defaultValue = -1000L;
        ResultFuture<Long> rfDefault = new ResultFuture<>();
        longStore.load(defaultValue, rfDefault);
        Long actualValue = rfDefault.get(100, TimeUnit.MILLISECONDS);
        
        assertTrue(defaultValue.equals(actualValue));
        
        Long saveValue = 100_000_000L;
        ResultFuture<Boolean> rfSaveOK = new ResultFuture<>();
        longStore.save(saveValue, rfSaveOK);
        
        rfSaveOK.get(100, TimeUnit.MILLISECONDS);
        
        ResultFuture<Long> rfLoad = new ResultFuture<>();
        longStore.load(defaultValue, rfLoad);
        Long persistedValue = rfLoad.get(100, TimeUnit.MILLISECONDS);
        
        assertTrue(saveValue.equals(persistedValue));
        
    }
    
    
    private void creatingLongStore(String name) {
        
        BarId serviceURL = new BarId("pod", "test", name);
        Store<Long> store = _sm.lookup(BarId.toStorage(serviceURL).fqURL()).as(Store.class, Long.class);
        longStore = new StoredVal<Long>(store, "a-long-value");

    }


}
