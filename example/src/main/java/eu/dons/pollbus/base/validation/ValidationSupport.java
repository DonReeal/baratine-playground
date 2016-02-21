package eu.dons.pollbus.base.validation;


import eu.dons.pollbus.base.AppException;
import io.baratine.core.Result;
import io.baratine.core.ServiceManager;

public class ValidationSupport implements IBeanValidator, Initializable<IBeanValidator> {

	public ValidationSupport(String validatorURL) {
		this.validatorURL = validatorURL;
	}
	
	ValidationSupport(IBeanValidator validator) {
		this.validatorURL = "";
		this.validator = validator;
	}
	
	private final String validatorURL;	
	private IBeanValidator validator;

	@Override
	public <T> void validate(T t, Result<T> result) throws AppException {
		validator.validate(t, result);		
	}
	
	@Override
	public IBeanValidator init() {
		if(validator == null) {
			validator = ServiceManager.current().lookup(validatorURL).as(IBeanValidator.class);
		}
		if(validator == null) 
			throw new IllegalStateException("validator not initialized");
		return validator;
	}

}
