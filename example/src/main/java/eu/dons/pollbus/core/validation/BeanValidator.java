package eu.dons.pollbus.core.validation;

import io.baratine.core.OnLoad;
import io.baratine.core.Result;
import io.baratine.core.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import eu.dons.pollbus.core.ApplicationException;

@Service("/base/beanvalidator")
public class BeanValidator implements IBeanValidator {
    
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
    public <T> void validate(T t, Result<T> result) throws ApplicationException {
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        if(violations.isEmpty()) result.complete(t);
        else throw new ApplicationException(mappedException(violations));  
    }
    
    
    private <T> IllegalArgumentException mappedException(Set<ConstraintViolation<T>> violations) {
        Map<Class<?>, String> violationTextsByRootBean = violations.stream().collect( Collectors.toMap(
                BeanValidator::rootBeanTypeOf,
                BeanValidator::toExceptionString,
                (exTextA, exTextB) -> exTextA + exTextB));
        
        final StringBuffer sb = new StringBuffer(String.format("=== Constraint Violation! === \n"));
        violationTextsByRootBean.forEach((clz, text) -> sb.append(String.format("violated RootBean: {%s} \n %s", clz, text)));

        return new IllegalArgumentException(sb.toString());
        
    }
    
    private static <T> String toExceptionString(ConstraintViolation<T> c) {
        return String.format("\n violation %s#%s: %s -- actual values was: %s ", c.getLeafBean(), c.getPropertyPath(), c.getMessage(), c.getInvalidValue());
    }

    private static <T> Class<?> rootBeanTypeOf(ConstraintViolation<T> c) {
        return c.getRootBean().getClass();
    }
}
