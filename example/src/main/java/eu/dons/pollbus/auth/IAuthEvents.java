package eu.dons.pollbus.auth;

import eu.dons.pollbus.user.boundary.LoginEvent;

public interface IAuthEvents {
	
	public LoginEvent onLogin();
	public LogoffEvent onLogoff();

}
