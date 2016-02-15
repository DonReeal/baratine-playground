package eu.dons.pollbus.core.validation;

import eu.dons.pollbus.core.ApplicationException;

public interface BeanValidatorSync extends IBeanValidator {
    <T> T validate(T t) throws ApplicationException;
}
