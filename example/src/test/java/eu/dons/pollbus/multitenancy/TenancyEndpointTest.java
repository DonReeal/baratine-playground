package eu.dons.pollbus.multitenancy;

import static org.junit.Assert.*;
import io.baratine.core.Lookup;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.caucho.junit.ConfigurationBaratine;
import com.caucho.junit.RunnerBaratine;

import eu.dons.pollbus.core.AppException;
import eu.dons.pollbus.core.validation.IBeanValidator;
import eu.dons.pollbus.multitenancy.control.TenantEndpoint;
import eu.dons.pollbus.multitenancy.control.TenantResource;

@RunWith(RunnerBaratine.class)
@ConfigurationBaratine(services={IBeanValidator.class, TenantEndpoint.class, TenantResource.class})
public class TenancyEndpointTest {

    @Lookup("/tenants")
    private ITenantEndpointSync tenants;
    
    @Test
    public void test() throws AppException {
        tenants.createTenant("theTenantId","don");
    }

}
