package eu.dons.pollbus.base.validation;

import eu.dons.pollbus.base.AppException;
import io.baratine.core.Result;

public interface IBeanValidator {
	
    <T> void validate(T t, Result<T> result) throws AppException;

}