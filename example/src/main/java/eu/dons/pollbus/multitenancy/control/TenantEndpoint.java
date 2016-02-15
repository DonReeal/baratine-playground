package eu.dons.pollbus.multitenancy.control;

import io.baratine.core.Lookup;
import io.baratine.core.OnLoad;
import io.baratine.core.Result;
import io.baratine.core.Service;
import io.baratine.core.ServiceManager;
import io.baratine.core.ServiceRef;

import java.util.Set;

import lombok.Builder;

import org.joda.time.DateTime;

import eu.dons.pollbus.core.ApplicationException;
import eu.dons.pollbus.core.validation.IBeanValidator;
import eu.dons.pollbus.multitenancy.boundary.ITenantEndpoint;
import eu.dons.pollbus.multitenancy.boundary.ITenantResource;
import eu.dons.pollbus.multitenancy.entity.Tenant;
import eu.dons.pollbus.multitenancy.entity.TenantId;

@Service("/tenants")
public class TenantEndpoint implements ITenantEndpoint {
    
    private ServiceRef _this;
    private Set<String> registeredTenantIds; // no eventual allowed here
    
    @Lookup("/base/beanvalidator")
    private IBeanValidator beanValidator;
    
    
    public void getTenant(TenantId id, Result<Tenant> result) {
        
    }
    
    @Override
    public void createTenant(String tenantId, String name, Result<Tenant> result) throws ApplicationException {
        
        TenantId id = new TenantId(tenantId);
        Tenant tenant = new Tenant(id, name, DateTime.now());
        
        // TODO: validate here or in the Instance?
        TenantResource tr = new TenantResource();
        tr.setValidator(beanValidator);
        ITenantResource tenantResource = ServiceManager.current()
                .newService()
                .address("local:///tenants/" + tenantId)
                .service(tr).build()
                .as(ITenantResource.class);
        
        tenantResource.create(tenant, x -> {
            registeredTenantIds.add(x.getId().getId());
            result.complete(x);
        });
    }

}
