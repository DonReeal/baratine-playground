package eu.dons.pollbus.auth;

import io.baratine.core.Result;

public interface IAuthService {
	
	void authUser(String userId, String digest, Result<Boolean> result);
	
}
