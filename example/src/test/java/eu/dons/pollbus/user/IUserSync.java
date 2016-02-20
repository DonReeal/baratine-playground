package eu.dons.pollbus.user;

import eu.dons.pollbus.core.AppException;
import eu.dons.pollbus.user.boundary.IUser;
import eu.dons.pollbus.user.entity.User;

public interface IUserSync extends IUser {
	
	String create(User user) throws AppException;
	User get();
	boolean delete();

}
