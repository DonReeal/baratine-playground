package eu.dons.pollbus.user.boundary;

import eu.dons.pollbus.base.AppException;
import io.baratine.core.Result;

public interface IUsers {

	void createUser(String login, String password, Result<String> result) throws AppException;

}