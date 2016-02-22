package eu.dons.pollbus.base.service;

import eu.dons.pollbus.base.data.Entity;
import eu.dons.pollbus.base.validation.IBeanValidator;
import eu.dons.pollbus.user.entity.User;
import io.baratine.core.OnInit;
import io.baratine.core.ServiceManager;

/**
 * TODO: replace this baseClass by a "ValidationSupport"-Class
 * 
 * ValidationSupport should describe how to initialize the used IBeanValidator see: {@link #onInit()}
 * and implement {@link IBeanValidator}
 * 
 * @author Bastian
 *
 */
public class ResourceBase<ID, DATA extends Entity<ID>> {
	

	private IBeanValidator validator;
	
	protected IBeanValidator getValidator() {
		return this.validator;
	}
	
	public void setValidator(IBeanValidator validator) {
		this.validator = validator;
	}
	
	@OnInit
	public void onInit() {
		validator = ServiceManager.current().lookup("/base/beanvalidator").as(IBeanValidator.class);
	}

}
