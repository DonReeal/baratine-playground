package eu.dons.baratine.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import io.baratine.core.ResultFuture;
import io.baratine.core.ServiceManager;
import io.baratine.store.Store;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.caucho.junit.ConfigurationBaratine;
import com.caucho.junit.ConfigurationBaratine.Log;
import com.caucho.junit.RunnerBaratine;

import eu.dons.baratine.persistence.mock.IServiceWithSequence;
import eu.dons.baratine.persistence.mock.ServiceWithSequence;

@RunWith(RunnerBaratine.class)
@ConfigurationBaratine(
        services={ServiceWithSequence.class},
        logLevel="FINE", //logNames={"eu.dons.baratine"}, 
        logs={@Log(name="eu.dons.baratine.persistence", level="FINEST")}
)
public class SequenceTest {

    @Inject
    ServiceManager _sm;

    @Test
    public void testInitial() {
        
        BarId serviceURL = new BarId("pod", "test", "test-sequence");
        Store<Long> store = _sm.lookup(BarId.toStorage(serviceURL).fqURL()).as(Store.class, Long.class);
        StoredVal<Long> storage = new StoredVal<Long>(store, "value-count");        
        
        Sequence seqImpl = Module.createSequence(storage);
        
        Sequence seq = _sm.newService()
                .address(serviceURL.fqURL())
                .service(seqImpl)
                .build().as(Sequence.class);
        
        ResultFuture<Long> first = new ResultFuture<Long>();
        seq.nextVal(first);
        Long one = first.get(2, TimeUnit.SECONDS);
        
        assertThat(one, is(Long.valueOf(1)));        
    }
    
    
    IServiceWithSequence svc;    
    
    @Test
    public void usableInService() {
        
        BarId serviceURL = new BarId("pod", "test", "service-with-sequence");
        assertThat(serviceURL.fqURL(), is("pod://test/service-with-sequence"));
        
        BarId storageURL = BarId.toStorage(serviceURL);
        assertThat(storageURL.fqURL(), is("store://test/service-with-sequence"));
        
        ServiceWithSequence o = new ServiceWithSequence(serviceURL);
        svc = _sm.newService().service(o).address(serviceURL.fqURL()).build().as(IServiceWithSequence.class);
        
        ResultFuture<Long> firstRF = new ResultFuture<Long>();
        svc.incrementSequence(firstRF);
        assertThat(firstRF.get(1,TimeUnit.SECONDS), is(Long.valueOf(1)));
        
        ResultFuture<Long> secondRF = new ResultFuture<Long>();
        svc.incrementSequence(secondRF);
        assertThat(secondRF.get(1,TimeUnit.SECONDS), is(Long.valueOf(2)));
        
    }

    
    
}
