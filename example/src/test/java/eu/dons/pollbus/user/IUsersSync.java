package eu.dons.pollbus.user;

import eu.dons.pollbus.base.AppException;
import eu.dons.pollbus.user.boundary.IUsers;

public interface IUsersSync extends IUsers {
	String createUser(String login, String password) throws AppException;

}
