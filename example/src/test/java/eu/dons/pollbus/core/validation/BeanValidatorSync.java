package eu.dons.pollbus.core.validation;

import eu.dons.pollbus.base.AppException;
import eu.dons.pollbus.base.validation.IBeanValidator;

public interface BeanValidatorSync extends IBeanValidator {
    <T> T validate(T t) throws AppException;
}
