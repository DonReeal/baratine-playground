package eu.dons.pollbus.base.validation;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import eu.dons.pollbus.base.AppException;
import io.baratine.core.OnLoad;
import io.baratine.core.Result;
import io.baratine.core.Service;

@Service("/base/beanvalidator")
public class BeanValidatorService implements IBeanValidator {
    
    private Validator validator;    
    
    @OnLoad
    void onLoad(Result<Boolean> result ) {
            ValidatorFactory f = Validation.buildDefaultValidatorFactory();    
            validator = f.getValidator();
            result.complete(true);
    }
    
    public void setValidatorImplementation(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <T> void validate(T t, Result<T> result) throws AppException {
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        if(violations.isEmpty()) result.complete(t);        
        else throw new AppException(toErrorMessage(violations));  
    }
    
    private <T> String toErrorMessage(Set<ConstraintViolation<T>> violations) {
    	
		Map<Class<?>, List<ConstraintViolation<?>>> violationsByRootBean = violations.stream()
				.collect(Collectors.groupingBy(ConstraintViolation::getRootBeanClass));
		
		StringBuilder errorMessageBuilder = new StringBuilder("Invalid data passed!");
		
		violationsByRootBean.forEach((rootBeanClass, violationList) -> {		
			// heading for each root bean that was invalid
			errorMessageBuilder.append(String.format("\n\\-invalid data in: %s", rootBeanClass.getName()));
			
			violationList.stream() // append list of violations for current 
				.map(BeanValidatorService::toFormattedExceptionString)
				.forEach(errorMessageBuilder::append);	
			
		});

    	return errorMessageBuilder.toString();
    }
    
    private static <T> String toFormattedExceptionString(ConstraintViolation<T> c) {
        return String.format("\n |-%s#%s: %s -- actual values was: '%s' ", c.getLeafBean().getClass().getSimpleName(), c.getPropertyPath(), c.getMessage(), c.getInvalidValue());
    }

}
