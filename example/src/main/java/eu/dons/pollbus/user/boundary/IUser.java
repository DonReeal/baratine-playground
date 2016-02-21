package eu.dons.pollbus.user.boundary;

import eu.dons.pollbus.core.AppException;
import eu.dons.pollbus.user.entity.User;
import io.baratine.core.Result;

public interface IUser {

	
	void create(User user, Result<String> result) throws AppException;
	void get(Result<User> result);
	void delete(Result<Boolean> result);

	// void login(String login, String digest, Result<Boolean> result);
	// void logout(Result<Boolean> result);

	// createPoll();
	// updatePoll();
	// publishPoll();

	// vote(PollId p, int choiceId);

}
