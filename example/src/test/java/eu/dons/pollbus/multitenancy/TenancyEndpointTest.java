package eu.dons.pollbus.multitenancy;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.caucho.junit.ConfigurationBaratine;
import com.caucho.junit.RunnerBaratine;

import eu.dons.pollbus.base.AppException;
import eu.dons.pollbus.base.validation.BeanValidatorService;
import eu.dons.pollbus.multitenancy.control.TenantEndpoint;
import eu.dons.pollbus.multitenancy.control.TenantResource;
import io.baratine.core.Lookup;

@RunWith(RunnerBaratine.class)
@ConfigurationBaratine(services={BeanValidatorService.class, TenantEndpoint.class, TenantResource.class})
public class TenancyEndpointTest {

    @Lookup("/tenants")
    private ITenantEndpointSync tenants;
    
    @Test
    public void test() throws AppException {
        tenants.createTenant("theTenantId","don");
    }

}
