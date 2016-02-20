package eu.dons.pollbus.multitenancy;

import eu.dons.pollbus.core.AppException;
import eu.dons.pollbus.multitenancy.boundary.ITenantEndpoint;
import eu.dons.pollbus.multitenancy.entity.Tenant;

public interface ITenantEndpointSync extends ITenantEndpoint {

    public abstract Tenant createTenant(String tenantId, String name) throws AppException;
}
