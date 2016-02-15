package eu.dons.pollbus.multitenancy.boundary;

import io.baratine.core.Result;
import eu.dons.pollbus.core.ApplicationException;
import eu.dons.pollbus.multitenancy.entity.Tenant;

public interface ITenantResource {

    void get(Result<Tenant> result);
    void put(Tenant tenant, Result<String> result) throws ApplicationException;
    void delete(Result<Boolean> result);
    void create(Tenant tenant, Result<Tenant> result) throws ApplicationException;

}