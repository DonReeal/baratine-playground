package eu.dons.pollbus.user.boundary;

import org.joda.time.DateTime;
import org.pure4j.annotations.immutable.ImmutableValue;
import org.pure4j.immutable.AbstractImmutableValue;

import eu.dons.pollbus.core.IEvent;

@ImmutableValue
public class LoginEvent extends AbstractImmutableValue<LoginEvent> implements IEvent {
	
	private String uuid;
	private String sessionId;
	private String userId;
	private DateTime time;
	
	@Override
	protected void fields(org.pure4j.immutable.AbstractImmutableValue.Visitor arg0, LoginEvent arg1) {
		
	}
	
	public LoginEvent(String uuid, String fromSessionId, String userId, DateTime loginTime) {
		this.uuid = uuid;
		this.sessionId = fromSessionId;
		this.userId = userId;
		
	}

}
