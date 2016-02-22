package eu.dons.pollbus.user.control;

import eu.dons.pollbus.base.AppException;
import eu.dons.pollbus.user.boundary.IUser;
import eu.dons.pollbus.user.boundary.IUsers;
import eu.dons.pollbus.user.entity.User;
import eu.dons.pollbus.user.entity.UserId;
import io.baratine.core.Journal;
import io.baratine.core.Modify;
import io.baratine.core.OnLookup;
import io.baratine.core.Result;
import io.baratine.core.Service;
import io.baratine.core.ServiceRef;

@Journal
@Service("/users")
public class UsersEndpoint implements IUsers {
	
	private long userIdCounter = 0L;
	
//	@Inject @Lookup("store:///users")
//	private Store<User> store;
//	
//	public void setStore(Store<User> store) {
//		this.store = store;
//	}	

	@Modify
	@Override
	public void createUser(String login, String password, Result<String> result) throws AppException {
		
		String userKey = Long.toString(++ userIdCounter);		
		IUser userInstanceSvc = ServiceRef.current().lookup("/" + userKey).as(IUser.class);
		User data = new User(new UserId(userKey), login, password);
		userInstanceSvc.create(data, result);
	}	

	@OnLookup
	public IUser onLookup(String path) {
		
		String key = path.substring(1);	
		return UserBean.builder()
				.userId(new UserId(key))
				// .db(new StoredVal<User>(store, key))
				.user(User.EMPTY)
				.build();		
	}
	
}
