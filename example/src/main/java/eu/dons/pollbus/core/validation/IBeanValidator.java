package eu.dons.pollbus.core.validation;

import io.baratine.core.Result;
import eu.dons.pollbus.core.ApplicationException;

public interface IBeanValidator {

    <T> void validate(T t, Result<T> result) throws ApplicationException;

}