package eu.dons.pollbus.multitenancy.boundary;

import io.baratine.core.Result;
import eu.dons.pollbus.core.AppException;
import eu.dons.pollbus.multitenancy.entity.Tenant;

public interface ITenantEndpoint {

    public abstract void createTenant(String tenantId, String name, Result<Tenant> result) throws AppException;

}