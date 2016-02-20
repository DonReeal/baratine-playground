package eu.dons.pollbus.core.validation;

import eu.dons.pollbus.core.AppException;
import io.baratine.core.Result;

public interface IBeanValidator {
	
    <T> void validate(T t, Result<T> result) throws AppException;

}