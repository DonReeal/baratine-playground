package eu.dons.pollbus.multitenancy.control;

import eu.dons.pollbus.base.validation.IBeanValidator;

public class ResourceBase {
    
    private IBeanValidator validator;
    
    public void setValidator(IBeanValidator validator) {
        this.validator = validator;
    }

    protected IBeanValidator getValidator() {
        return validator;
        
    }
    
    

}
