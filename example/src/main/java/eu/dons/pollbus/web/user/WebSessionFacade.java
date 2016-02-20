package eu.dons.pollbus.web.user;

import javax.inject.Inject;

import io.baratine.core.Lookup;
import io.baratine.core.Result;
import io.baratine.core.Service;

import eu.dons.pollbus.auth.IAuthService;
import eu.dons.pollbus.user.boundary.IUser;


@Service("session:///web-session")
public class WebSessionFacade {
	
	@Inject @Lookup("public://auth/auth-service")
	private IAuthService authService;
	
	private boolean isLoggedIn = false;
	
	private IUser user;
	
	public void login(String login, String digest, Result<Boolean> result) {
		authService.authUser(login, digest, result.from((authSuccess, r) -> {
			if(!authSuccess)
				result.complete(false);
			else {
				onLogin(x -> r.complete(true));
			}
		}));		 
	}
	
	private boolean onLogin(Result<Void> result) {
		
		// lookup User		
		return true;
	}
	
}
