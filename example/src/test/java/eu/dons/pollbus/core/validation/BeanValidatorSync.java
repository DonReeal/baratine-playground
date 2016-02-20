package eu.dons.pollbus.core.validation;

import eu.dons.pollbus.core.AppException;

public interface BeanValidatorSync extends IBeanValidator {
    <T> T validate(T t) throws AppException;
}
