package eu.dons.pollbus.core;

import javax.inject.Inject;

import eu.dons.pollbus.core.validation.IBeanValidator;
import io.baratine.core.Lookup;

public class ResourceBase {
	
	@Inject @Lookup("/base/beanvalidator")
	protected IBeanValidator validator;
	
	public void setValidator(IBeanValidator validator) {
		this.validator = validator;
	}

}
