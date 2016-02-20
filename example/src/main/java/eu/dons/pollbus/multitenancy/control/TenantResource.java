package eu.dons.pollbus.multitenancy.control;

import io.baratine.core.Modify;
import io.baratine.core.Result;

import eu.dons.pollbus.core.AppException;
import eu.dons.pollbus.multitenancy.boundary.ITenantResource;
import eu.dons.pollbus.multitenancy.entity.Tenant;

public class TenantResource extends ResourceBase implements ITenantResource {
 
    private Tenant tenant;
    
    @Override
    public void get(Result<Tenant> result) {
        result.complete(tenant);
    }

    @Modify
    @Override
    public void put(Tenant data, Result<String> result) throws AppException {
        getValidator().validate(data, v -> {
            this.tenant = v;
            result.complete(data.getId().getId());
         });        
    }

    @Modify
    @Override
    public void delete(Result<Boolean> result) {
        this.tenant = null;
        result.complete(true);
    }

    @Modify
    @Override
    public void create(Tenant tenant, Result<Tenant> result) throws AppException {
        
        
        getValidator().validate(tenant, result);
        
        getValidator().validate(tenant, v -> {
            this.tenant = v;
            result.complete(tenant);
        });
    
    }
}
